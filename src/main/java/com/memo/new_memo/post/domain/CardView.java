package com.memo.new_memo.post.domain;

import java.util.List;

import com.memo.new_memo.comment.domain.CommentView;
import com.memo.new_memo.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CardView {


	// 글 1개
	private Post post;
	
	// 글쓴이 정보
	private UserEntity user;
	
	// 댓글 리스트
	private List<CommentView> commentList;
	
	// 댓글 개수
	private int commentCount;
	
	
}
