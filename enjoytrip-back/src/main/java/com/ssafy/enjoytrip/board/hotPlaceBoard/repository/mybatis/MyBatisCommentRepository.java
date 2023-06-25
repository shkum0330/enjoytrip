package com.ssafy.enjoytrip.board.hotPlaceBoard.repository.mybatis;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.CommentDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.repository.CommentRepository;
import com.ssafy.enjoytrip.members.model.MembersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MyBatisCommentRepository implements CommentRepository {
    private final CommentMapper commentMapper;

    @Override
    public void writeComment(CommentDto dto) throws SQLException {
        commentMapper.writeComment(dto);
    }

    @Override
    public List<CommentDto> findByArticleNo(int articleNo) throws SQLException {
        return commentMapper.findByArticleNo(articleNo);
    }

    @Override
    public Optional<CommentDto> findByCommentId(int commentId) throws SQLException {
        return commentMapper.findByCommentId(commentId);
    }

    @Override
    public void modifyComment(CommentDto dto,int commentId) throws SQLException {
        commentMapper.modifyComment(dto,commentId);
    }

    @Override
    public void deleteComment(int commentId) throws SQLException {
        commentMapper.deleteComment(commentId);
    }

    @Override
    public void likeComment(int commentId) throws SQLException {
        log.info("repository에서 commentId = {}",commentId);
        commentMapper.likeComment(commentId);
    }

    @Override
    public void unlikeComment(int commentId) throws SQLException {
        commentMapper.unlikeComment(commentId);
    }

}
