package com.ssafy.enjoytrip.members.controller;

import com.ssafy.enjoytrip.jwt.JwtTokenProvider;
import com.ssafy.enjoytrip.jwt.LoginInfo;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.service.MembersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "Members", description = "유저 API Document")
@RequiredArgsConstructor
public class MembersController {

    private final MembersService membersService;
    private final JwtTokenProvider jwtTokenProvider;
    // 회원가입 페이지 이동
    @GetMapping("/join")
    @Operation(summary = "회원가입 GET", description = "회원가입 페이지로 이동")
    public String join(){
        return "join";
    }
    // 회원가입
    /**
     {"userName":"금싸피",
     "userId":"ssafy",
     "userPassword":"1234",
     "emailId":"kumssafy",
     "emailDomain":"kakao.com"}
     */
    @PostMapping("/join")
    @Operation(summary = "회원가입 POST", description = "회원가입")
    public String join(@RequestBody MembersDto dto , HttpSession session, Model model) throws Exception {
        log.info("membersController join - {}",dto);
        membersService.join(dto);
        return "회원가입 성공";
    }

    // 로그인 페이지 이동
    @GetMapping("/login")
    @Operation(summary = "로그인 GET", description = "로그인 페이지로 이동")
    public String login(){
        return "login";
    }

    // 로그인
    // {"userId":"ssafy", "userPassword":"1234" }
//    @ResponseBody
    @PostMapping("/login")
    @Operation(summary = "로그인 POST", description = "로그인")
    public LoginInfo login(@RequestBody MembersDto dto, HttpSession session) throws Exception {
        log.info("membersController login - {}", dto);
        LoginInfo login =membersService.login(dto);
        String userId=jwtTokenProvider.getUserId(login.getAccessToken());
        login.setRole(membersService.findById(userId).get().getRole());
        log.info("로그인 아이디: {}",userId);

        return login;
    }
    @GetMapping(value = "/logoutuser")
    @Operation(summary = "로그아웃 GET", description = "로그아웃")
    public void logout(HttpServletRequest request) throws Exception {
        log.info("membersController 로그아웃");
        membersService.logout(request);

    }

    @GetMapping("/member/details")
    @Operation(summary = "회원 상세보기 GET", description = "마이페이지")
    MembersDto memberDetails(HttpServletRequest request) throws Exception {
        String token=jwtTokenProvider.resolveToken(request,"Access");
        if(!jwtTokenProvider.validateToken(token)){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        String userId= jwtTokenProvider.getUserId(token);
        MembersDto member=membersService.findById(userId).get();
        return member;
    }
    @GetMapping("/member/update")
    MembersDto updateMember(HttpServletRequest request) throws Exception {
        String token=jwtTokenProvider.resolveToken(request,"Access");
        if(!jwtTokenProvider.validateToken(token)){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        String userId= jwtTokenProvider.getUserId(token);
        MembersDto member=membersService.findById(userId).get();
        return member;
    }
    @PostMapping("/member/update")
    @Operation(summary = "회원정보 수정 POST", description = "닉네임, 비밀번호, 이메일 변경")
    Optional<MembersDto> updateMember(HttpServletRequest request, @RequestBody MembersDto dto) throws Exception {
        String token=jwtTokenProvider.resolveToken(request,"Access");
        if(!jwtTokenProvider.validateToken(token)){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        String userId= jwtTokenProvider.getUserId(token);
        membersService.updateMember(userId,dto);
        log.info("{}",membersService.findById(userId));
        return membersService.findById(userId);
    }
    @GetMapping("/member/delete")
    @Operation(summary = "회원 탈퇴 GET", description = "회원 DB에서 삭제")
    void deleteMember(HttpServletRequest request) throws Exception {
        String token=jwtTokenProvider.resolveToken(request,"Access");
        if(!jwtTokenProvider.validateToken(token)){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        String userId= jwtTokenProvider.getUserId(token);
        membersService.deleteMember(userId);
    }
}
