package com.ssafy.enjoytrip.members.controller;

import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.service.MembersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "관리자 API Document")
public class AdminController {
    @Autowired
    private MembersService membersService;
    @GetMapping("/manageMem")
    @ResponseBody
    @Operation(summary = "회원 목록 조회 GET", description = "회원 목록 조회")
    public List<MembersDto> listMembers() throws Exception {
        log.info("adminController manageMem" );
        return membersService.listMembers();
    }
    @GetMapping("/manageMem/{userId}")
    @Operation(summary = "관리자 회원 상세보기 GET", description = "회원정보 상세보기 페이지로 이동")
    public Optional<MembersDto> viewMemberInfo(@PathVariable String userId) throws Exception {
        log.info("adminController modifyMem");
        MembersDto member=membersService.findById(userId).get();
        return membersService.findById(userId);
    }
    @GetMapping("/modifyMem/{userId}")
    @Operation(summary = "관리자 회원정보 수정 GET", description = "회원정보 수정 페이지로 이동")
    public String updateMember(@PathVariable String userId) throws Exception {
        log.info("adminController modifyMem");
        return "회원정보 수정 페이지로 이동";
    }
    @PostMapping("/modifyMem/{userId}")
    @Operation(summary = "관리자 회원정보 수정 POST", description = "회원정보 수정")
    public String updateMember(@PathVariable String userId,@RequestBody MembersDto dto) throws Exception {
        log.info("adminController modifyMem {}",dto);
        membersService.updateMember(userId,dto);
        return "회원정보 수정 완료 by 관리자";
    }
    @GetMapping( "/deleteMem/{userId}")
    @Operation(summary = "관리자 회원 삭제 GET", description = "회원 삭제")
    public String deleteMember(@PathVariable String userId) throws Exception {
        log.info("adminController deleteMem");
        membersService.deleteMember(userId);
        return "회원정보 삭제 완료 by 관리자";
    }
}
