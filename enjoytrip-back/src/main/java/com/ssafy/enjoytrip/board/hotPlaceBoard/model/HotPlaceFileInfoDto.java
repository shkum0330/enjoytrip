package com.ssafy.enjoytrip.board.hotPlaceBoard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotPlaceFileInfoDto {

	private String saveFolder;
	private String originalFile;
	private String saveFile;

	public String getSaveFolder() {
		return saveFolder;
	}
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}
}
