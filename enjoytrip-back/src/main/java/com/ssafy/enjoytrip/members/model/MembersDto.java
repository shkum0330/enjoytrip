package com.ssafy.enjoytrip.members.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter @Setter
@ToString
public class MembersDto implements UserDetails {
//    @NotEmpty
//    private Long sequenceId;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String emailId;
    @NotEmpty
    private String emailDomain;
    private String joinDate;
    private String role;

    // security fields
    private Collection<? extends GrantedAuthority> authorities;
    private String username;  // credential(email)
    private String password;  // credential


    // 회원가입 전용
    public MembersDto(String userId, String userName, String userPassword, String emailId, String emailDomain) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.emailId = emailId;
        this.emailDomain = emailDomain;
    }

    public MembersDto(String userId, String userName, String userPassword, String emailId, String emailDomain, String joinDate, String role) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.emailId = emailId;
        this.emailDomain = emailDomain;
        this.joinDate = joinDate;
        this.role = role;
    }

    // 로그인 전용
    public MembersDto(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public MembersDto(String userId, String userPassword, int userClass) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    // 정보 수정
    public MembersDto(String userName, String userPassword, String emailId, String emailDomain) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.emailId = emailId;
        this.emailDomain = emailDomain;
    }

    public MembersDto(String userId, String userName, String emailId, String emailDomain, String joinDate, int userClass) {
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.emailDomain = emailDomain;
        this.joinDate = joinDate;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setUsername(String username) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
