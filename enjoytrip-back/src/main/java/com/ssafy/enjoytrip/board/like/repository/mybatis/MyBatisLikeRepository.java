package com.ssafy.enjoytrip.board.like.repository.mybatis;

import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceLikeDto;
import com.ssafy.enjoytrip.board.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MyBatisLikeRepository implements LikeRepository {

    private final LikeMapper likeMapper;
    @Override
    public Optional<HotPlaceLikeDto> findByMemberAndArticle(String userId, int articleNo) throws SQLException {
        return likeMapper.findByMemberAndArticle(userId,articleNo);
    }
    @Override
    public Optional<HotPlaceCommentLikeDto> findByMemberAndComment(String userId, int commentId) throws SQLException {
        log.info("likemapper userId={}, commentId={}",userId,commentId);
        return likeMapper.findByMemberAndComment(userId,commentId);
    }


    @Override
    public void save(HotPlaceLikeDto dto) throws SQLException {
        likeMapper.saveArticleLike(dto);
    }

    @Override
    public void delete(HotPlaceLikeDto dto) throws SQLException {
        likeMapper.deleteArticleLike(dto);
    }

    @Override
    public void save(HotPlaceCommentLikeDto dto) throws SQLException {
        likeMapper.save(dto);
    }

    @Override
    public void delete(HotPlaceCommentLikeDto dto) throws SQLException {
        likeMapper.delete(dto);
    }
}
