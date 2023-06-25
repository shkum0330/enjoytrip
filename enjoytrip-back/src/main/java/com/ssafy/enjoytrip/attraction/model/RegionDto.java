package com.ssafy.enjoytrip.attraction.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionDto {
    private int sidoCode;
    private String sidoName;
    private int gugunCode;
    private String gugunName;
}
