package com.ssafy.enjoytrip.board.hotPlaceBoard.repository.mybatis;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.CommentDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceBoardDto;
import com.ssafy.enjoytrip.members.model.MembersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    void writeComment(CommentDto commentDto) throws SQLException;
    List<CommentDto> findByArticleNo(int articleNo) throws SQLException;
    Optional<CommentDto> findByCommentId(int commentId) throws SQLException;
    void modifyComment(@Param("comment") CommentDto dto, @Param("commentId") int commentId) throws SQLException;
    void deleteComment(int commentId) throws SQLException;
    void likeComment(int commentId) throws SQLException;
    void unlikeComment(int commentId) throws SQLException;
}
