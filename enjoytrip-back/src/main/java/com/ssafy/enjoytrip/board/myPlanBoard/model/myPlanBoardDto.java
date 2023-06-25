package com.ssafy.enjoytrip.board.myPlanBoard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class myPlanBoardDto {

	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private String userName;
	private int hit;
	private String registerTime;
	private List<myPlanFileInfoDto> fileInfos;

}
