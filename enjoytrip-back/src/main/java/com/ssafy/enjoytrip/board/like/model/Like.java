package com.ssafy.enjoytrip.board.like.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(includeFieldNames = true)
public class Like {
    private int id;
    private String userId;
    public Like(String userId) {
        this.userId = userId;
    }

}
