package com.ssafy.enjoytrip.jwt;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    Optional<RefreshToken> findByUserId(String userId);
    void save(RefreshToken token);
    void updateRefreshToken(RefreshToken token);
}
