package com.ssafy.enjoytrip.attraction.controller;

import com.ssafy.enjoytrip.attraction.model.*;
import com.ssafy.enjoytrip.attraction.service.AttractionService;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceBoardDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceFileInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "Attraction", description = "관광지 API Document")
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("/")
    @Operation(summary = "index 페이지 GET", description = "index 페이지에서 여행지 리스트 출력")
    public List<AttractionDto> index() throws Exception {
        List<AttractionDto> attractionList1 = attractionService.getRandomAttraction(10);
        List<AttractionDto> attractionList2 = attractionService.getRandomAttraction(10);
        attractionList1.addAll(attractionList2);
        return attractionList1;
    }

    @GetMapping("/attraction/search-region")
    @Operation(summary = "gugun 리스트 GET", description = "sido 코드로 gugun 코드 반환")
    public List<RegionDto> searchByRegion() throws Exception {
        return attractionService.getRegions();
    }

    @PostMapping("/attraction/search")
    @Operation(summary = "관광지 검색 페이지 POST", description = "검색 조건으로 관광지 검색")
    public List<AttractionDto> searchByRegion(@RequestBody SearchCond cond) throws Exception {
        log.info("검색 조건={}",cond);
        List<AttractionDto> attractionList=attractionService.searchByRegion(cond);
        return attractionList;
    }
    @GetMapping("/attraction/sido")
    @Operation(summary = "sido 리스트 GET", description = "sidoCode, sidoName 정보 반환")
    public List<SidoDto> getSidoList() throws Exception {
        return attractionService.getSido();
    }

    @GetMapping("/attraction/gugun")
    @Operation(summary = "gugun 리스트 GET", description = "sidoCode로 gugunCode, sidoName 정보 반환")
    public List<GugunDto> getGugunList(@RequestParam("sidoCode") int sidoCode) throws Exception {
        return attractionService.getGugun(sidoCode);
    }



}
