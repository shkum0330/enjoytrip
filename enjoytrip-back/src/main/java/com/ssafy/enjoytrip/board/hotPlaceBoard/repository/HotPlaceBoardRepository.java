package com.ssafy.enjoytrip.board.hotPlaceBoard.repository;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceBoardDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceFileInfoDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HotPlaceBoardRepository {
    void writeArticle(HotPlaceBoardDto boardDto) throws SQLException;
    void registerFile(HotPlaceBoardDto boardDto) throws Exception;
    Optional<HotPlaceBoardDto> findById(int articleNo) throws SQLException;
    List<HotPlaceBoardDto> findBySubject(String subject) throws SQLException;
    List<HotPlaceBoardDto> findByContent(String content) throws SQLException;
    List<HotPlaceBoardDto> findByWriter(String userName) throws SQLException;
    List<HotPlaceBoardDto> listArticle() throws SQLException;
    int getTotalArticleCount(Map<String, Object> param) throws SQLException;
    HotPlaceBoardDto getArticle(int articleNo) throws SQLException;
    void likeArticle(int articleNo) throws SQLException;
    void unlikeArticle(int articleNo) throws SQLException;
    void updateHit(int articleNo) throws SQLException;
    void modifyArticle(HotPlaceBoardDto boardDto) throws SQLException;
    void deleteFile(int articleNo) throws Exception;
    void deleteArticle(int articleNo) throws SQLException;

    List<HotPlaceFileInfoDto> fileInfoList(int articleNo) throws Exception;
}
