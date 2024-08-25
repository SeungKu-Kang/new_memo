package com.memo.new_memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

	@GetMapping("/sign-up-view")
	public String signUpView() {
		return "user/signUp";
	}
	
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn";
	}
	
	@GetMapping("/sign-out")
	public String signOut(HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		return "redirect:/user/sign-in-view";
	}
	
	@GetMapping("/my-page-view")
	public String myPageView() {
		return "user/myPage";
	}
}
