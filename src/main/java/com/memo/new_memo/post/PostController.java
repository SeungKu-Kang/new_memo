package com.memo.new_memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.new_memo.post.bo.PostBO;
import com.memo.new_memo.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	PostBO postBO;
	
	 @GetMapping("/post-list-view") 
	 public String postListView(
			 @RequestParam(value = "prevId", required = false) Integer prevIdParam,
			 @RequestParam(value = "nextId", required = false) Integer nextIdParam,
			 HttpSession session, Model model) {
		 
		 // 로그인 여부 확인 -> 상관없음
		 // userId와 무관하게 모든 글 가져오기
		 
		 // DB 조회 - 글 목록 $ 이전, 다음 클릭
		 List<Post> postList = postBO.getPostList(prevIdParam, nextIdParam);
		 
		 int prevId = 0;
		 int nextId = 0;
		 if (postList.isEmpty() == false) {
			 prevId = postList.get(0).getId();
			 nextId = postList.get(postList.size() - 1).getId();
			 
			 if (postBO.isPrevLastPage(prevId)) {
				 prevId = 0;
			 }
			 
			 if (postBO.isNextLastPage(nextId)) {
				 nextId = 0;
			 }
		 }
		 
		 // 모델에 담기
		 model.addAttribute("prevId",prevId);
		 model.addAttribute("nextId",nextId);
		 model.addAttribute("postList", postList);
		 
		 return "post/postList";
	 }
	 
	 @GetMapping("/post-create-view")
	 public String postCreateView() {
		 return "post/postCreate";
	 }
	 @GetMapping("/post-detail-view")
	 public String postDetailView(
			 @RequestParam("postId") int postId,
			 Model model, HttpSession session) {
		 
		 // db 조회 - userId, postId
		 //int userId = (int)session.getAttribute("userId");
		 Post post = postBO.getPostByPostId(postId);
		 
		 // model에 담기
		 model.addAttribute("post", post);
		 
		 return "post/postDetail";
	 }
	 
}
