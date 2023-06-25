package com.ssafy.enjoytrip.attraction.service;

import com.ssafy.enjoytrip.attraction.model.*;
import com.ssafy.enjoytrip.attraction.repository.AttractionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttractionServiceImpl implements AttractionService{

	private final AttractionMapper attractionMapper;

	@Override
	public List<AttractionDto> getRandomAttraction(int num) throws Exception {
		return attractionMapper.getRandAttractionInfo(num);
	}

	@Override
	public List<AttractionDto> getAttractionInfo(int contentId) throws Exception {
		return attractionMapper.getAllAttractionInfo(contentId);
	}

	@Override
	public List<RegionDto> getRegions() throws Exception {
		return attractionMapper.getRegions();
	}

	@Override
	public List<AttractionDto> searchByRegion(SearchCond cond) throws Exception {
		return attractionMapper.searchByRegion(cond);
	}

	@Override
	public List<SidoDto> getSido() throws Exception {
		return attractionMapper.getSido();
	}

	@Override
	public List<GugunDto> getGugun(int sidoCode) throws SQLException {
		return attractionMapper.getGugun(sidoCode);
	}
}
