package com.ssafy.enjoytrip.board.like.repository.mybatis;

import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceLikeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.Optional;

@Mapper
public interface LikeMapper {
    Optional<HotPlaceLikeDto> findByMemberAndArticle(@Param("userId") String userId,@Param("articleNo") int articleNo) throws SQLException;
    Optional<HotPlaceCommentLikeDto> findByMemberAndComment(@Param("userId") String userId,@Param("commentId") int commentId) throws SQLException;
    void saveArticleLike(HotPlaceLikeDto dto) throws SQLException;
    void deleteArticleLike(HotPlaceLikeDto dto) throws SQLException;
    void save(HotPlaceCommentLikeDto dto) throws SQLException;
    void delete(HotPlaceCommentLikeDto dto) throws SQLException;
}
