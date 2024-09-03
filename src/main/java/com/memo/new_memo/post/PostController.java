package com.memo.new_memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.new_memo.comment.bo.CommentBO;
import com.memo.new_memo.comment.domain.CommentView;
import com.memo.new_memo.post.bo.PostBO;
import com.memo.new_memo.post.domain.CardView;
import com.memo.new_memo.post.domain.Post;
import com.memo.new_memo.user.bo.UserBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	PostBO postBO;
	
	@Autowired
	UserBO userBO;
	
	@Autowired
	CommentBO commentBO;
	
	 @GetMapping("/post-list-view") 
	 public String postListView(
			 @RequestParam(value = "prevId", required = false) Integer prevIdParam,
			 @RequestParam(value = "nextId", required = false) Integer nextIdParam,
			 HttpSession session, Model model) {
		 
		 Integer userId = (Integer)session.getAttribute("userId");
		 List<CardView> cardViewList = postBO.generateCardViewList(userId);
		 
		 // DB 조회 - 글 목록 
		 // 이전, 다음 클릭
		 
		 
		 List<Post> postList = postBO.getPostList(prevIdParam, nextIdParam);
		 int prevId = 0;
		 int nextId = 0;
		 if (postList.isEmpty() == false) { // 글목록이 비어있지 않을 때 페이징 정보 세팅
			prevId = postList.get(0).getId(); // 첫번째 칸 id
			nextId = postList.get(postList.size() - 1).getId(); // 마지막칸 id
				
			// 이전 방향의 끝인가? 그러면 0
			// prevId	테이블의 제일 큰 숫자와 같으면 이전의 끝페이지
			if (postBO.isPrevLastPage(prevId)) {
				prevId = 0;
			}
				
			// 다음 방향의 끝인가? 그러면 0
			// nextId와 테이블의 제일 작은 숫자가 같으면 다음의 끝페이지
			if (postBO.isNextLastPage(nextId)) {
				nextId = 0;
			}
		}
		 
		 // 모델에 담기
		 model.addAttribute("prevId", prevId);
		 model.addAttribute("nextId", nextId);
		 model.addAttribute("cardViewList", cardViewList);
		 
		 
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
		 Post post = postBO.getPostByPostId(postId);
		 Integer userId = (Integer)session.getAttribute("userId");
		 List<CardView> cardViewList = postBO.generateCardViewList(userId);
		 List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(postId);
		 
		 // model에 담기
		 model.addAttribute("post", post);
		 model.addAttribute("cardViewList", cardViewList);
		 model.addAttribute("commentViewList", commentViewList);
		 
		 return "post/postDetail";
	 }
	 
}
