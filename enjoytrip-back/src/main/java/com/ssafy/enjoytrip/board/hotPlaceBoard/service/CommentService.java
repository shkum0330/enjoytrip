package com.ssafy.enjoytrip.board.hotPlaceBoard.service;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.CommentDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.members.model.MembersDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    void writeComment(CommentDto dto , MembersDto login) throws Exception;
    List<CommentDto> findByArticleNo(int articleNo) throws Exception;
    Optional<CommentDto> findByCommentId(int commentId) throws Exception;
    void modifyComment(CommentDto dto,int commentId) throws Exception;
    void deleteComment(int commentId) throws Exception;
    void likeComment(HotPlaceCommentLikeDto like) throws Exception;
    void unlikeComment(HotPlaceCommentLikeDto like) throws Exception;
    boolean isLike(HotPlaceCommentLikeDto like) throws Exception;
}
