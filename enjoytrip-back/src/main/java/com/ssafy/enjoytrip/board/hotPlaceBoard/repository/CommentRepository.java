package com.ssafy.enjoytrip.board.hotPlaceBoard.repository;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.CommentDto;
import com.ssafy.enjoytrip.members.model.MembersDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    void writeComment(CommentDto dto) throws SQLException;
    List<CommentDto> findByArticleNo(int articleNo) throws SQLException;
    Optional<CommentDto> findByCommentId(int commentId) throws SQLException;
    void modifyComment(CommentDto dto,int commentId) throws SQLException;
    void deleteComment(int commentId) throws SQLException;
    void likeComment(int commentId) throws SQLException;
    void unlikeComment(int commentId) throws SQLException;
}
