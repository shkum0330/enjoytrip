package com.ssafy.enjoytrip.members.service;

import com.ssafy.enjoytrip.jwt.JwtTokenProvider;
import com.ssafy.enjoytrip.jwt.RefreshToken;
import com.ssafy.enjoytrip.jwt.RefreshTokenMapper;
import com.ssafy.enjoytrip.jwt.LoginInfo;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.repository.mybatis.MembersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembersServiceImpl implements MembersService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MembersMapper membersMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void join(MembersDto dto) throws Exception {
        // ID 중복검사
        if(membersMapper.findById(dto.getUserId()).isPresent()){
            throw new RuntimeException("이미 같은 아이디가 있습니다.");
        }

        // 비밀번호 암호화
        dto.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        log.info("membersService join success - {}",dto);
        membersMapper.join(dto);
    }

    @Override
    public LoginInfo login(MembersDto dto) throws Exception {

        // 아이디 검사
        MembersDto account = membersMapper.findById(dto.getUserId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 아이디입니다.")
        );
        log.info("로그인 검사 {} {}", dto.getUserPassword(), account.getUserPassword());
        // 비밀번호 검사
        if(!passwordEncoder.matches(dto.getUserPassword(), account.getUserPassword())) {
            throw new RuntimeException("패스워드가 일치하지 않습니다.");
        }
        // 토큰 생성
        LoginInfo loginInfo =new LoginInfo(jwtTokenProvider.createToken(account.getUserId(),"Access"),
                jwtTokenProvider.createToken(account.getUserId(),"Refresh"));
        log.info("액세스 토큰: {}, 리프레시 토큰: {}", loginInfo.getAccessToken(), loginInfo.getRefreshToken());

        // Refresh 토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenMapper.findByUserId(dto.getUserId());
        // 있으면 업데이트
        if(refreshToken.isPresent()) {
            refreshTokenMapper.updateRefreshToken(refreshToken.get().updateToken(loginInfo.getRefreshToken()));
        }else { // 없으면 새 토큰 생성
            RefreshToken newToken = new RefreshToken(loginInfo.getRefreshToken(), dto.getUserId());
            refreshTokenMapper.save(newToken);
        }
        log.info("membersService login - {}",dto);
        return loginInfo;
    }

    @Override
    public void logout(HttpServletRequest request) throws Exception {
        String accessToken = request.getHeader("Access-Token");
        log.info("{}",accessToken);
        Long expiration = jwtTokenProvider.getExpiration(accessToken);

        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        log.info("로그아웃 성공");
    }

    @Override
    public List<MembersDto> listMembers() throws Exception {
        log.info("membersService admin-listUser");
        return membersMapper.listMembers();
    }

//    @Override
//    public Optional<MembersDto> viewMemberInfo(String userId) throws Exception {
//        return membersMapper.findById(userId);
//    }

    @Override
    public Optional<MembersDto> findById(String userId) throws Exception {
        return membersMapper.findById(userId);
    }

    @Override
    public void updateMember(String userId,MembersDto dto) throws Exception {
        dto.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
        membersMapper.updateMember(userId, dto);
    }

    @Override
    public void deleteMember(String userId) throws Exception {
        membersMapper.deleteMember(userId);
    }

}
