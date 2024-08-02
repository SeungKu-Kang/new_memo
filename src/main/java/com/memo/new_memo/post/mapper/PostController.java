package com.memo.new_memo.post.mapper;

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
	 
}
