package com.ssafy.enjoytrip.board.tripInfoBoard.service;

import com.ssafy.enjoytrip.board.tripInfoBoard.model.tripInfoBoardDto;
import com.ssafy.enjoytrip.util.PageNavigation;

import java.util.List;
import java.util.Map;



public interface tripInfoBoardService {

	void writeArticle(tripInfoBoardDto boardDto) throws Exception;
	List<tripInfoBoardDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	tripInfoBoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(tripInfoBoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo, String path) throws Exception;


}
