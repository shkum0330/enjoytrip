package com.ssafy.enjoytrip.board.like.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class HotPlaceCommentLikeDto extends Like{
    private int objectType;
    private int commentId;
    public HotPlaceCommentLikeDto(String userId,int commentId) {
        super(userId);
        this.commentId=commentId;
        this.objectType = 1;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }
}
