package com.ssafy.enjoytrip.board.like.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class HotPlaceLikeDto extends Like{
    private int objectType;
    private int articleNo;
    public HotPlaceLikeDto(String userId, int articleNo) {
        super(userId);
        this.articleNo=articleNo;
        this.objectType = 0;
    }
    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }
}
