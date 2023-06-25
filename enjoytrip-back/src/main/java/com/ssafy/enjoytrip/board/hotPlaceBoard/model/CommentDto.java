package com.ssafy.enjoytrip.board.hotPlaceBoard.model;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private int commentId;
    private int articleNo;
    private String userId;
    private String userName;
    private String content;
    private int likes;
    private String registerTime;

    public CommentDto(String content) {
        this.content = content;
    }
    public CommentDto(int commentId,String userId, String content) {
        this.commentId=commentId;
        this.userId = userId;
        this.content = content;
    }
    public CommentDto(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public CommentDto(int commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }

}

