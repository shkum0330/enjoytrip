package com.ssafy.enjoytrip.jwt;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RefreshToken{
    @NotBlank
    private Long refreshTokenId;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String UserId;

    public RefreshToken(String refreshToken, String UserId) {
        this.refreshToken = refreshToken;
        this.UserId = UserId;
    }
    public RefreshToken updateToken(String token){
        this.refreshToken=token;
        return this;
    }
}
