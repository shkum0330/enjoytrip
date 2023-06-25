package com.ssafy.enjoytrip.board.myPlanBoard.service;

import com.ssafy.enjoytrip.board.myPlanBoard.mapper.myPlanBoardMapper;
import com.ssafy.enjoytrip.board.myPlanBoard.model.myPlanBoardDto;
import com.ssafy.enjoytrip.board.myPlanBoard.model.myPlanFileInfoDto;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class myPlanBoardServiceImpl implements myPlanBoardService {

	private final myPlanBoardMapper boardMapper;

	@Override
	@Transactional
	public void writeArticle(myPlanBoardDto boardDto) throws Exception {
		System.out.println("글입력 전 dto : " + boardDto);
		boardMapper.writeArticle(boardDto);
		System.out.println("글입력 후 dto : " + boardDto);
		List<myPlanFileInfoDto> fileInfos = boardDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			boardMapper.registerFile(boardDto);
		}
	}

	@Override
	public List<myPlanBoardDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		System.out.println("key는 "+key+" 입니다.");
		if("userid".equals(key))
			key = "b.user_id";
		else if("article_no".equals(key))
			key = "b.article_no";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return boardMapper.listArticle(param);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public myPlanBoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(myPlanBoardDto boardDto) throws Exception {
		// TODO : BoardDaoImpl의 modifyArticle 호출
		boardMapper.modifyArticle(boardDto);
	}

	@Override
	@Transactional
	public void deleteArticle(int articleNo, String path) throws Exception {
		// TODO : BoardDaoImpl의 deleteArticle 호출
		List<myPlanFileInfoDto> fileList = boardMapper.fileInfoList(articleNo);
		boardMapper.deleteFile(articleNo);
		boardMapper.deleteArticle(articleNo);
		for(myPlanFileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
	}
}
