package com.memo.new_memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.new_memo.user.entity.UserEntity;
import com.memo.new_memo.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	UserRepository userRepository;
	
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId,password);
	}
	
	// id 중복확인할 때 쓰일 메소드
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	// input : 4개의 파라미터
	// output: UserEntity
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	}
	
	public UserEntity getUserEntityById(int userId) {
		return userRepository.findById(userId);
	}
	
	public boolean checkPassword(int userId, String getPassword, String realPassword) {
		if (getPassword.equals(realPassword)) {
			return true;
		} else {
			return false;
		}
	}

	public UserEntity updateUserByUserId(int userId, String loginId, String password, String name, String email) {
		UserEntity user = userRepository.findById(userId);
		user = user.toBuilder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build();
		return userRepository.save(user);
	}
	/*
	 * public UserEntity updateUserByUserId(int userId, String userLoginId, String
	 * password, String name, String email) { return
	 * userRepository.updateUserByuserId(userId); }
	 */
}
