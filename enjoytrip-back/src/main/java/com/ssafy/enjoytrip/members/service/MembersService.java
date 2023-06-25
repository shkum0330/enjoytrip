package com.ssafy.enjoytrip.members.service;

import com.ssafy.enjoytrip.jwt.LoginInfo;
import com.ssafy.enjoytrip.members.model.MembersDto;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface MembersService {
    void join(MembersDto dto) throws Exception;
    LoginInfo login(MembersDto dto) throws Exception;
    void logout(HttpServletRequest request) throws Exception;
    List<MembersDto> listMembers() throws Exception;
    Optional<MembersDto> findById(String userId) throws Exception;
    void updateMember(String userId,MembersDto dto) throws Exception;
    void deleteMember(String userId) throws Exception;
}
