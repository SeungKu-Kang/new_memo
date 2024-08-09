package com.memo.new_memo.post.bo;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.new_memo.common.FileManagerService;
import com.memo.new_memo.post.domain.Post;
import com.memo.new_memo.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired
	PostMapper postMapper;
	
	@Autowired
	FileManagerService fileManagerService;
	
	// 페이징 정보 필드(limit)
	private static final int POST_MAX_SIZE = 3;
	
	// 글 단건 가져오기
	// input : userId, postId
	// output: Post or null
	public Post getPostByUserIdPostId(int userId, int postId) {
		return postMapper.selectPostByUserIdPostId(userId, postId);
	}
	
	// 글 목록 뿌리기
	// input : userId, prevId, nextId
	// output: List<Post>
	
	// input: 로그인된 사람의 userId
		// output: List<Post>
		public List<Post> getPostList(Integer prevId, Integer nextId) {
			// 게시글 번호 10 9 8 | 7 6 5 | 4 3 2 | 1
			// 만약 4 3 2 페이지에 있을 때
			// 1) 다음: 2보다 작은 3개를 DESC정렬
			// 2) 이전: 4보다 큰 4개를 ASC정렬 => 5 6 7 => BO에서 reverse 7 6 5
			// 3) 페이징 X: 최신순 3개 DESC정렬
			Integer standardId = null; // 기준이 되는 postId
			String direction = null; // 방향
			if (prevId != null) { // 2) 이전
				standardId = prevId;
				direction = "prev";
				
				List<Post> postList = postMapper.selectPostList(standardId, direction, POST_MAX_SIZE);
				// [5 6 7] => [7 6 5]
				Collections.reverse(postList); // 리스트 뒤집고 저장
				
				return postList;
			} else if (nextId != null) { // 1) 다음
				standardId = nextId;
				direction = "next";
			}
			
			// 3) 페이징 정보 X, 1) 다음
			return postMapper.selectPostList(standardId, direction, POST_MAX_SIZE);
		}
		
	public boolean isPrevLastPage(int prevId) {
		int maxPostId = postMapper.selectPostIdAsSort("DESC");
		return maxPostId == prevId;
	}
	
	public boolean isNextLastPage(int nextId) {
		int minPostId = postMapper.selectPostIdAsSort("ASC");
		return minPostId == nextId;
	}
	
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		String imagePath = null;
		
		if (file != null) {
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		postMapper.insertPost(userId, subject, content, imagePath);
	}
}
