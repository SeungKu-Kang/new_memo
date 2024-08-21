package com.memo.new_memo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.new_memo.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Integer>{

	// JPQL
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
	
	public UserEntity findByLoginId(String loginId);
	
	public UserEntity findById(int userId);
	
	
}
