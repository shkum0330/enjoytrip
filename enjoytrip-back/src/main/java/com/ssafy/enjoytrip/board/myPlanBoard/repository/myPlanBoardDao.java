package com.ssafy.enjoytrip.board.myPlanBoard.repository;

import com.ssafy.enjoytrip.board.myPlanBoard.model.myPlanBoardDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public interface myPlanBoardDao {

	void writeArticle(myPlanBoardDto boardDto) throws SQLException;
	List<myPlanBoardDto> listArticle(Map<String, Object> param) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	myPlanBoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	void modifyArticle(myPlanBoardDto boardDto) throws SQLException;
	void deleteArticle(int articleNo) throws SQLException;
	
}
