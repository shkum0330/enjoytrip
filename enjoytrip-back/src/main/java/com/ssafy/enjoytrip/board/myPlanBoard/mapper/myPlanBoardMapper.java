package com.ssafy.enjoytrip.board.myPlanBoard.mapper;

import com.ssafy.enjoytrip.board.myPlanBoard.model.myPlanBoardDto;
import com.ssafy.enjoytrip.board.myPlanBoard.model.myPlanFileInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface myPlanBoardMapper {

    void writeArticle(myPlanBoardDto boardDto) throws SQLException;

    void registerFile(myPlanBoardDto boardDto) throws Exception;
    List<myPlanBoardDto> listArticle(Map<String, Object> param) throws SQLException;
    int getTotalArticleCount(Map<String, Object> param) throws SQLException;
    myPlanBoardDto getArticle(int articleNo) throws SQLException;
    void updateHit(int articleNo) throws SQLException;
    void modifyArticle(myPlanBoardDto boardDto) throws SQLException;
    void deleteFile(int articleNo) throws Exception;
    void deleteArticle(int articleNo) throws SQLException;

    List<myPlanFileInfoDto> fileInfoList(int articleNo) throws Exception;

}
