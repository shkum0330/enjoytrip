package com.ssafy.enjoytrip.board.tripInfoBoard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class tripInfoBoardDto {

	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private String userName;
	private int hit;
	private String registerTime;
	private List<tripInfoFileInfoDto> fileInfos;

	@Override
	public String toString() {
		return "tripInfoBoardDto{" +
				"articleNo=" + articleNo +
				", userId='" + userId + '\'' +
				", subject='" + subject + '\'' +
				", content='" + content + '\'' +
				", hit=" + hit +
				", registerTime='" + registerTime + '\'' +
				'}';
	}
}
