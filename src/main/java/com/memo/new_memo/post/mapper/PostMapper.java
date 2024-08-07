package com.memo.new_memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.new_memo.post.domain.Post;

@Mapper
public interface PostMapper {
	public List<Map<String, Object>> selectPostListTest();
	
	// postDetail 가져오기
	public Post selectPostByUserIdPostId(
			@Param("userId") int userId,
			@Param("postId") int postId);
}


