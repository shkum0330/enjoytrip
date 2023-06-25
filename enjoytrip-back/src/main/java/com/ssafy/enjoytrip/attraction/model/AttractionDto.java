package com.ssafy.enjoytrip.attraction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttractionDto {
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String firstImage;
	private String description;
	private int sidoCode;
	private int gugunCode;
	private double latitude;
	private double longitude;

	public AttractionDto(int contentId, String title, String addr1, String description,String firstImage, double latitude, double longitude) {
		this.contentId=contentId;
		this.title = title;
		this.addr1 = addr1;
		this.firstImage = firstImage;
		this.description=description;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public AttractionDto(String title, String addr1, String firstImage, double latitude, double longitude) {
		this.title = title;
		this.addr1 = addr1;
		this.firstImage = firstImage;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
