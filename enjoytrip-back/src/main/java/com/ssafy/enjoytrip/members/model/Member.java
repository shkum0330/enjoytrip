package com.ssafy.enjoytrip.members.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class Member {
    @NotEmpty
    private Long sequenceId;
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
}
