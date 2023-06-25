package com.ssafy.enjoytrip.members.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
public class MemberUpdateForm {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String userPassword;
    @NotEmpty
    private String emailId;
    @NotEmpty
    private String emailDomain;
}
