package com.memo.new_memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.new_memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {

	@Autowired
	PostBO postBO;

	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject, 
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) {

		
		  int userId = (int)session.getAttribute("userId"); 
		  String userLoginId =(String)session.getAttribute("userLoginId");
		 
		// db insert 
		 postBO.addPost(userId, userLoginId, subject, content, file);
		 
		// 응답값 내리기
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");

		return result;
	}
	
	@PostMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) {
		
		
		 int userId = (int)session.getAttribute("userId"); 
		 String userLoginId =(String)session.getAttribute("userLoginId");
		 
		 // db update
		 postBO.updatePostByPostId(userId, userLoginId, postId, subject, content, file);
		 
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		
		// DB delete
		postBO.deletePostByPostIdUserId(postId, userId);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
