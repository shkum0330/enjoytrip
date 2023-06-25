package com.ssafy.enjoytrip.board.tripInfoBoard.mapper;

import com.ssafy.enjoytrip.board.tripInfoBoard.model.tripInfoBoardDto;
import com.ssafy.enjoytrip.board.tripInfoBoard.model.tripInfoFileInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface tripInfoBoardMapper {

    void writeArticle(tripInfoBoardDto boardDto) throws SQLException;

    void registerFile(tripInfoBoardDto boardDto) throws Exception;
    List<tripInfoBoardDto> listArticle(Map<String, Object> param) throws SQLException;
    int getTotalArticleCount(Map<String, Object> param) throws SQLException;
    tripInfoBoardDto getArticle(int articleNo) throws SQLException;
    void updateHit(int articleNo) throws SQLException;
    void modifyArticle(tripInfoBoardDto boardDto) throws SQLException;
    void deleteFile(int articleNo) throws Exception;
    void deleteArticle(int articleNo) throws SQLException;

    List<tripInfoFileInfoDto> fileInfoList(int articleNo) throws Exception;

}
