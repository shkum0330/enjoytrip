package com.ssafy.enjoytrip.jwt;

import lombok.*;

@Getter
@NoArgsConstructor
public class LoginInfo {
    private String accessToken;
    private String refreshToken;
    private String role;
    public LoginInfo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
