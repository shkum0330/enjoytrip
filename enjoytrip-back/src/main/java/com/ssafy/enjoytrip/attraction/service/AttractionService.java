package com.ssafy.enjoytrip.attraction.service;

import com.ssafy.enjoytrip.attraction.model.*;

import java.util.List;

public interface AttractionService {
	List<AttractionDto> getRandomAttraction(int num) throws Exception;
	List<AttractionDto> getAttractionInfo(int content_id) throws Exception;
	List<RegionDto> getRegions() throws Exception;
	List<AttractionDto> searchByRegion(SearchCond cond) throws Exception;

	List<SidoDto> getSido() throws Exception;

	List<GugunDto> getGugun(int sidoCode) throws Exception;
}
