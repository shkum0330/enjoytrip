package com.ssafy.enjoytrip.board.hotPlaceBoard.controller;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.CommentDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.service.CommentService;
import com.ssafy.enjoytrip.board.hotPlaceBoard.service.HotPlaceBoardService;
import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.jwt.JwtTokenProvider;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.service.MembersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/hotPlaceBoard/comment")
@Tag(name = "HotPlaceComment", description = "핫플 댓글 API Document")
public class CommentController {
    @Autowired
    private HotPlaceBoardService boardService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MembersService membersService;

    @PostMapping("/write/{articleNo}")
    @Operation(summary = "댓글 작성 POST", description = "파라미터로 페이지의 articleNo 넘겨야함")
    public void write(@RequestBody CommentDto dto,@PathVariable String articleNo, HttpServletRequest request) throws Exception {
        String userId= getUserId(request);
        String userName=membersService.findById(userId).get().getUsername();

        dto.setArticleNo(Integer.parseInt(articleNo));
        commentService.writeComment(dto,membersService.findById(userId).get());
    }
    @GetMapping("findByArticleNo/{articleNo}")
    @Operation(summary = "댓글 찾기 GET", description = "PathVariable: commentId")
    public List<CommentDto> findByArticleNo(@PathVariable int articleNo) throws Exception {
        return commentService.findByArticleNo(articleNo);
    }

    @GetMapping("findByCommentId/{commentId}")
    @Operation(summary = "댓글 찾기 GET", description = "PathVariable: commentId")
    public Optional<CommentDto> findByCommentId(@PathVariable String commentId) throws Exception {
        return commentService.findByCommentId(Integer.parseInt(commentId));
    }

    @PostMapping("/modify/{commentId}")
    @Operation(summary = "댓글 수정 POST", description = "파라미터: commentDto, commentId")
    public void modifyComment(@RequestBody CommentDto dto,@PathVariable String commentId, HttpServletRequest request) throws Exception {
        String userId= getUserId(request);
        Optional<MembersDto> login=membersService.findById(userId);
        log.info("로그인 유저: {}",login);
        log.info("findByCommentId로 찾은 글쓴이: {}",commentService.findByCommentId(Integer.parseInt(commentId)).get().getUserId());
        if(login==null){
            throw new RuntimeException("로그인하세요.");
        }
        if(!commentService.findByCommentId(Integer.parseInt(commentId)).get().getUserId().equals(login.get().getUserId())){ // 다른 아이디로 댓글을 수정하려 할 때
            throw new RuntimeException("자신이 쓴 댓글만 수정할 수 있습니다.");
        }
        commentService.modifyComment(dto,Integer.parseInt(commentId));
    }
    @GetMapping("/delete/{commentId}")
    @Operation(summary = "댓글 삭제 POST", description = "파라미터: commentId")
    public void deleteComment(@PathVariable String commentId, HttpServletRequest request) throws Exception {
        String userId= getUserId(request);;
        Optional<MembersDto> login=membersService.findById(userId);
        if(login==null){
            throw new RuntimeException("로그인하세요.");
        }
        if(!commentService.findByCommentId(Integer.parseInt(commentId)).get().getUserId().equals(login.get().getUserId())){ // 다른 아이디로 댓글을 수정하려 할 때
            throw new RuntimeException("자신이 쓴 댓글만 삭제할 수 있습니다.");
        }
        commentService.deleteComment(Integer.parseInt(commentId));
    }

    @GetMapping("/like/{commentId}")
    @Operation(summary = "댓글 좋아요 GET", description = "로그인해야만 좋아요 가능. 1번 요청하면 좋아요(true), 2번 요청하면 취소(false)")
    public boolean likeHotPlaceComment(@PathVariable String commentId, HttpServletRequest request) throws Exception {
        String userId= getUserId(request);
        String userName=membersService.findById(userId).get().getUsername();

        HotPlaceCommentLikeDto dto=new HotPlaceCommentLikeDto(userId,Integer.parseInt(commentId));
        log.info("컨트롤러 HotPlaceCommentLikeDto={}",dto);
        log.info("컨트롤러 userId={}",dto.getUserId());
        if(commentService.isLike(dto)){
            log.info("좋아요");
            commentService.likeComment(dto);
            return true;
        }
        else {
            log.info("좋아요 취소");
            commentService.unlikeComment(dto);
            return false;
        }
    }
    private String getUserId(HttpServletRequest request){
        String token=jwtTokenProvider.resolveToken(request,"Access");
        if(!jwtTokenProvider.validateToken(token)){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        String userId= jwtTokenProvider.getUserId(token);
        return userId;
    }
}
