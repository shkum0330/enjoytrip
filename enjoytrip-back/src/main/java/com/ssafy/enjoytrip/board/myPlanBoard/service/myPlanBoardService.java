package com.ssafy.enjoytrip.board.myPlanBoard.service;

import com.ssafy.enjoytrip.board.myPlanBoard.model.myPlanBoardDto;
import com.ssafy.enjoytrip.util.PageNavigation;

import java.util.List;
import java.util.Map;



public interface myPlanBoardService {

	void writeArticle(myPlanBoardDto boardDto) throws Exception;
	List<myPlanBoardDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	myPlanBoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(myPlanBoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo, String path) throws Exception;


}
