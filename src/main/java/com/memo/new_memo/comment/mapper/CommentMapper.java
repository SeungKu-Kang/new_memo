package com.memo.new_memo.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.new_memo.comment.domain.Comment;

@Mapper
public interface CommentMapper {

	public int insertComment(
			@Param("postId") int postId, 
			@Param("userId") int userId, 
			@Param("content") String content);
	
	public List<Comment> selectCommentList();
	
	public List<Comment> selectCommentListByPostId(int postId);
	
	public void deleteCommentById(int id);
	
	public int selectCommentCountByPostId(int postId);
}
