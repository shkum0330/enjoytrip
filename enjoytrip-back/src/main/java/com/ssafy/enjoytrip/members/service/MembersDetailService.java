package com.ssafy.enjoytrip.members.service;

import com.ssafy.enjoytrip.jwt.JwtTokenProvider;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.repository.mybatis.MembersMapper;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MembersDetailService implements UserDetailsService {
    private final MembersMapper membersMapper;
    @Value("jwt.secret.access.key")
    private String accessSecretKey;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        username=Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(username).getBody().getSubject();
        log.info("username={}, result={}",username,membersMapper.findById(username));
        MembersDto member=membersMapper.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        member.setUsername(username);
        member.setPassword(member.getUserPassword());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        log.info("role={}",member.getRole());
        member.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(member.getRole())));
        log.info("authorities={}",member.getAuthorities());
        return member;
    }
}
