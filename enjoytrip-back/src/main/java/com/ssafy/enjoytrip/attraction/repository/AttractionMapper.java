package com.ssafy.enjoytrip.attraction.repository;

import com.ssafy.enjoytrip.attraction.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface AttractionMapper {

    List<AttractionDto> getRandAttractionInfo(int num) throws SQLException;
    List<AttractionDto> getAllAttractionInfo(int contentId) throws SQLException;
    List<RegionDto> getRegions() throws SQLException;
    List<AttractionDto> searchByRegion(SearchCond cond) throws SQLException;
    List<SidoDto> getSido() throws SQLException;

    List<GugunDto> getGugun(int sidoCode) throws SQLException;
}
