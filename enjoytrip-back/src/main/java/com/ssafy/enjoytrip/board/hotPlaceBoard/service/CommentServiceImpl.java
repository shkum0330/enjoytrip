package com.ssafy.enjoytrip.board.hotPlaceBoard.service;

import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.board.like.repository.LikeRepository;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.CommentDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.repository.CommentRepository;
import com.ssafy.enjoytrip.board.hotPlaceBoard.repository.HotPlaceBoardRepository;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.repository.mybatis.MembersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    private final MembersMapper membersMapper;
    private final CommentRepository commentRepository;
    private final HotPlaceBoardRepository hotPlaceBoardRepository;
    private final LikeRepository likeRepository;

    @Override
    public void writeComment(CommentDto dto, MembersDto login) throws Exception {
        dto.setUserId(login.getUserId());;
        commentRepository.writeComment(dto);
    }

    @Override
    public List<CommentDto> findByArticleNo(int articleNo) throws Exception {
        return commentRepository.findByArticleNo(articleNo);
    }


    @Override
    public Optional<CommentDto> findByCommentId(int commentId) throws Exception {
        return commentRepository.findByCommentId(commentId);
    }

    @Override
    public void modifyComment(CommentDto dto,int commentId) throws Exception {
        commentRepository.modifyComment(dto,commentId);
    }

    @Override
    public void deleteComment(int commentId) throws Exception {
        commentRepository.deleteComment(commentId);
    }

    @Override
    public void likeComment(HotPlaceCommentLikeDto like) throws Exception {

        MembersDto member = membersMapper.findById(like.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + like.getUserId()));

        CommentDto comment = commentRepository.findByCommentId(like.getCommentId())
                .orElseThrow(() -> new NotFoundException("Could not found comment id : " + like.getCommentId()));


        likeRepository.save(like);
        commentRepository.likeComment(comment.getCommentId());
    }

    @Override
    public void unlikeComment(HotPlaceCommentLikeDto like) throws Exception {
        MembersDto member = membersMapper.findById(like.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + like.getUserId()));

        CommentDto comment = commentRepository.findByCommentId(like.getCommentId())
                .orElseThrow(() -> new NotFoundException("Could not found comment id : " + like.getCommentId()));

        likeRepository.delete(like);
        commentRepository.unlikeComment(comment.getCommentId());
    }

    @Override
    public boolean isLike(HotPlaceCommentLikeDto like) throws Exception {
        MembersDto member = membersMapper.findById(like.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + like.getUserId()));

        CommentDto comment = commentRepository.findByCommentId(like.getCommentId())
                .orElseThrow(() -> new NotFoundException("Could not found comment id : " + like.getCommentId()));

        if (!likeRepository.findByMemberAndComment(member.getUserId(), comment.getCommentId()).isPresent()){ // 이미 좋아요를 했으면 취소
            return true;
        }
        return false;
    }
}
