package com.memo.new_memo.comment.domain;

import com.memo.new_memo.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CommentView {

	// 댓글 1개
	private Comment comment;
	
	// 댓글쓴이
	private UserEntity user;
}
