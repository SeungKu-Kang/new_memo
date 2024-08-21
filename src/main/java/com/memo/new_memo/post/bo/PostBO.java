package com.memo.new_memo.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.new_memo.comment.bo.CommentBO;
import com.memo.new_memo.comment.domain.CommentView;
import com.memo.new_memo.common.FileManagerService;
import com.memo.new_memo.post.domain.CardView;
import com.memo.new_memo.post.domain.Post;
import com.memo.new_memo.post.mapper.PostMapper;
import com.memo.new_memo.user.bo.UserBO;
import com.memo.new_memo.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {

	@Autowired
	PostMapper postMapper;
	
	@Autowired
	UserBO userBO;
	
	@Autowired
	CommentBO commentBO;
	
	@Autowired
	FileManagerService fileManagerService;
	
	// 페이징 정보 필드(limit)
	//private static final int POST_MAX_SIZE = 3;
	
	// 글 단건 가져오기
	// input : userId, postId
	// output: Post or null
	/*
	 * public Post getPostByUserIdPostId(int userId, int postId) { return
	 * postMapper.selectPostByUserIdPostId(userId, postId); }
	 */
	
	// 위에꺼아니면 이거 둘중 하나 지울거
	public Post getPostByPostId(int postId) {
		return postMapper.selectPostByPostId(postId);
	}
	
	
	
	// 글 목록 뿌리기
	// input : userId, prevId, nextId
	// output: List<Post>
	
	// input: 로그인된 사람의 userId
	// output: List<Post>
		/* public List<CardView> getPostList(Integer prevId, Integer nextId) {
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
				
				List<CardView> postList = postMapper.selectPostList(standardId, direction, POST_MAX_SIZE);
				// [5 6 7] => [7 6 5]
				Collections.reverse(postList); // 리스트 뒤집고 저장
				
				return postList;
			} else if (nextId != null) { // 1) 다음
				standardId = nextId;
				direction = "next";
			}
			
			// 3) 페이징 정보 X, 1) 다음
			return postMapper.selectPostList(standardId, direction, POST_MAX_SIZE);
		*/
	
	// 작성자 loginId 꺼내기 위한 method 0821
	// input : X
	// output: List<Post>
	public List<CardView> generateCardViewList(Integer userId) {
		List<CardView> cardViewList = new ArrayList<>();
		
		List<Post> postList = postMapper.selectPostList();
		for (Post post : postList) {
			CardView card = new CardView();
			
			// post
			card.setPost(post);
			
			// user
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			card.setUser(user);
			
			// comment
			List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(post.getId());
			card.setCommentList(commentViewList);
			
			int commentCount = commentBO.getCommentCountByPostId(post.getId());
			card.setCommentCount(commentCount);
			
			cardViewList.add(card);
		}
		return cardViewList;
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
	
	public void updatePostByPostId( 
			int userId, String loginId,
			int postId, String subject, 
			String content, MultipartFile file) {
		
		
		//!!!! selectPostByUserIdPostId 대신에 UserId빼고 해보는 중
		// 업데이트할 기존 글을 가져온다
		// 이유 1) 이미지 교체 시 기존 글에 있던 imagePath 삭제하기 위함
		// 이유 2) 업데이트 대상이 있는지 확인하기 위함
		Post post = postMapper.selectPostByPostId(postId);
		// System.out.println(); => 사용해서는 안됨 : 각 사용자는 쓰레드인데 이 코드를 적게 되면 해당 쓰레드가 서버를 점유하게 되어 서버가 느려진다.
		if (post == null) {
			log.warn("[글 수정] post is null. userId:{}, postId:{}", userId, postId); // userId가 {}안에 들어가게 된다.
			return;
		}
		
		// 파일이 있으면 
		// 1) 새 이미지를 업로드
		// 2) 1번 단계가 성공하면 기존 이미지가 있을 때 삭제
		String imagePath = null;
				
		if (file != null) {
			// 새 이미지 업로드
			imagePath = fileManagerService.uploadFile(file, loginId);
					
			// 업로드 성공 시 (null 아님) 기존 이미지가 있으면 제거 (AND조건)
			if (imagePath != null && post.getImagePath() != null) {
				// 폴더와 이미지 제거(서버에서)
				fileManagerService.deleteFile(post.getImagePath()); // ()안에 자동완성되는 것이 imagePath인데 이건 업로드할 새 이미지이므로 아님.
			}
		}
				
		// db update
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}
	
	
	//!!!! selectPostByUserIdPostId 대신에 UserId빼고 해보는 중
	public void deletePostByPostIdUserId(int postId, int userId) {
		// 기존글 가져오기(이미지 존재시 삭제하기 위해)
		Post post = postMapper.selectPostByPostId(postId);
		if (post == null) {
			log.info("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
				
		// post 먼저 db delete
		int rowCount = postMapper.deletePostByPostId(postId);
				
		// 이미지가 존재하면 삭제, 삭제된 행도 1개 일 때 
		if (rowCount > 0 && post.getImagePath() != null) {
			fileManagerService.deleteFile(post.getImagePath());
					
		}
	}
}
