package com.memo.new_memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.new_memo.common.EncryptUtils;
import com.memo.new_memo.user.bo.UserBO;
import com.memo.new_memo.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	// ID 중복확인 API
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// db 조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// 응답값
		Map<String,Object> result = new HashMap<>();
		result.put("code", 200);
		if (user != null) {
			result.put("is_duplicated_id", true);
		} else {
			result.put("is_duplicated_id", false);
		}
		return result;
	}
	
	// 로그인 API
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// password 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB 조회  - loginId, 해싱된 비밀번호 => UserEntity
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId,hashedPassword);
		
		// 존재하는 정보인지 확인 및 응답값 내리기
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			result.put("code",200);
			result.put("result", "성공");
		} else {
			result.put("code", 403);
			result.put("error_message","존재하지 않는 사용자입니다.");
		}
		return result;
		 
	}
	
	// 회원가입
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// password hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// db insert
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// 응답값 내리기
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
}
