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
	
	// postListView에 뿌릴 모든 글
	public List<Post> selectPostList(
			@Param("prevIdParam") int prevIdParam,
			@Param("nextIdParam") int nextIdParam);
	
	public int selectPostIdAsSort(
			@Param("sort") String sort);
	
	public void insertPost(
			@Param("userId") int userId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	public List<Post> selectPostList(
			@Param("standardId") Integer standardId,
			@Param("direction") String direction,
			@Param("limit") int limit);
	
	public void updatePostByPostId(
			@Param("postId") int postId, 
			@Param("subject") String subject, 
			@Param("content") String content, 
			@Param("imagePath") String imagePath);
	
	public int deletePostByPostId(int postId);
}


