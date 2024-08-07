package com.memo.new_memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.new_memo.post.domain.Post;
import com.memo.new_memo.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	PostMapper postMapper;
	
	// 글 단건 가져오기
	// input : userId, postId
	// output: Post or null
	public Post getPostByUserIdPostId(int userId, int postId) {
		return postMapper.selectPostByUserIdPostId(userId, postId);
	}
	
	// 글 목록 뿌리기
	// input : userId, prevId, nextId
	// output: List<Post>
	
	public List<Post> getPostListByUserId() {
		return 
	}
}
