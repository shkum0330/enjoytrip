package com.ssafy.enjoytrip.board.hotPlaceBoard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotPlaceBoardDto {

	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private String userName;
	private int hit;
	private int likes;
	private String registerTime;
	private List<HotPlaceFileInfoDto> fileInfos;

	public HotPlaceBoardDto(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public HotPlaceBoardDto(int articleNo, String userId, String subject, String content,int likes) {
		this.articleNo = articleNo;
		this.userId = userId;
		this.subject = subject;
		this.content = content;
		this.likes=likes;
	}

	@Override
	public String toString() {
		return "hotPlaceBoardDto{" +
				"articleNo=" + articleNo +
				", userId='" + userId + '\'' +
				", subject='" + subject + '\'' +
				", content='" + content + '\'' +
				", hit=" + hit +
				", registerTime='" + registerTime + '\'' +
				'}';
	}
}
