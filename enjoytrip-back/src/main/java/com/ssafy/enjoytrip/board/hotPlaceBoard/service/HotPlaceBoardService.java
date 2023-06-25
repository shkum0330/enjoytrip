package com.ssafy.enjoytrip.board.hotPlaceBoard.service;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceBoardDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceLikeDto;
import com.ssafy.enjoytrip.util.PageNavigation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface HotPlaceBoardService {

	void writeArticle(HotPlaceBoardDto boardDto) throws Exception;

//	List<HotPlaceBoardDto> listArticle(Map<String, String> map) throws Exception;
	List<HotPlaceBoardDto> listArticle() throws Exception;
	Optional<HotPlaceBoardDto> findById(int articleNo) throws Exception;
	List<HotPlaceBoardDto> findBySubject(String subject) throws Exception;
	List<HotPlaceBoardDto> findByContent(String content) throws Exception;
	List<HotPlaceBoardDto> findByWriter(String userName) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	HotPlaceBoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void likeArticle(HotPlaceLikeDto dto) throws Exception;
	void unlikeArticle(HotPlaceLikeDto dto) throws Exception;
	boolean isLike(HotPlaceLikeDto like) throws Exception;
	void modifyArticle(HotPlaceBoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo, String path) throws Exception;


}
