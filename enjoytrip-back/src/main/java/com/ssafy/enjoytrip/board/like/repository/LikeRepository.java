package com.ssafy.enjoytrip.board.like.repository;

import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceLikeDto;
import com.ssafy.enjoytrip.board.like.model.Like;

import java.sql.SQLException;
import java.util.Optional;

public interface LikeRepository {
    Optional<HotPlaceLikeDto> findByMemberAndArticle(String userId, int articleNo) throws SQLException;
    Optional<HotPlaceCommentLikeDto> findByMemberAndComment(String userId, int commentId) throws SQLException;
    void save(HotPlaceLikeDto dto) throws SQLException;
    void delete(HotPlaceLikeDto dto) throws SQLException;
    void save(HotPlaceCommentLikeDto dto) throws SQLException;
    void delete(HotPlaceCommentLikeDto dto) throws SQLException;
}
