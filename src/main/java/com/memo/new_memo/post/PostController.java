package com.memo.new_memo.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/post")
@Controller
public class PostController {

	
	 @GetMapping("/post-list-view") 
	 public String postListView() {
		 return "/";
	 }
	 
	 @GetMapping("/post-create-view")
	 public String postCreateView() {
		 return "post/postCreate";
	 }
	 @GetMapping("/post-detail-view")
	 public String postDetailView() {
		 
	 }
	 
}
