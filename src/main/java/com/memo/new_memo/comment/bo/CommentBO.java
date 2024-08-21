package com.memo.new_memo.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.new_memo.comment.domain.Comment;
import com.memo.new_memo.comment.domain.CommentView;
import com.memo.new_memo.comment.mapper.CommentMapper;
import com.memo.new_memo.user.bo.UserBO;
import com.memo.new_memo.user.entity.UserEntity;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;
	
	// 댓글 저장
	public void addComment(int postId, int userId, String content) {
		commentMapper.insertComment(postId, userId, content);
	}
	
	public List<CommentView> generateCommentViewListByPostId(int postId) {
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 댓글들 가져옴
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		// 반복문 순회 => Comment -> CommentView   => list에 담음
		for (Comment comment : commentList) {
			CommentView commentView = new CommentView();
			
			// 댓글 1개
			commentView.setComment(comment);
			
			// 댓글쓰니
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user);
			
			//!!!!!! list에 넣기
			commentViewList.add(commentView);
		}
		
		return commentViewList;
	}
	
	// 댓글 삭제
	public void deleteCommentById(int id) {
		commentMapper.deleteCommentById(id);
	}
	
	// 댓글 개수
	public int getCommentCountByPostId(int postId) {
		return commentMapper.selectCommentCountByPostId(postId);
	}
}
