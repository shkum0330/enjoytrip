package com.ssafy.enjoytrip.board.hotPlaceBoard.service;

import com.ssafy.enjoytrip.board.hotPlaceBoard.model.CommentDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceBoardDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceFileInfoDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.repository.HotPlaceBoardRepository;
import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceLikeDto;
import com.ssafy.enjoytrip.board.like.repository.LikeRepository;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.repository.mybatis.MembersMapper;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class HotPlaceBoardServiceImpl implements HotPlaceBoardService {

	private final HotPlaceBoardRepository hotPlaceBoardRepository;
	private final MembersMapper membersMapper;
	private final LikeRepository likeRepository;
	@Override
	@Transactional
	public void writeArticle(HotPlaceBoardDto boardDto) throws Exception {
		log.info("글입력 전 dto : {}", boardDto);
		hotPlaceBoardRepository.writeArticle(boardDto);
		log.info("글입력 후 dto : {}", boardDto);
		List<HotPlaceFileInfoDto> fileInfos = boardDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			log.info("파일 저장...");
			hotPlaceBoardRepository.registerFile(boardDto);
		}
	}

	@Override
	public Optional<HotPlaceBoardDto> findById(int articleNo) throws Exception {
		return hotPlaceBoardRepository.findById(articleNo);
	}

	@Override
	public List<HotPlaceBoardDto> findBySubject(String subject) throws Exception {
		return hotPlaceBoardRepository.findBySubject(subject);
	}

	@Override
	public List<HotPlaceBoardDto> findByContent(String content) throws Exception {
		return hotPlaceBoardRepository.findByContent(content);
	}

	@Override
	public List<HotPlaceBoardDto> findByWriter(String userName) throws Exception {
		return hotPlaceBoardRepository.findByWriter(userName);
	}

	@Override
	public List<HotPlaceBoardDto> listArticle() throws Exception {
		return hotPlaceBoardRepository.listArticle();
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
		int totalCount = hotPlaceBoardRepository.getTotalArticleCount(param);
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
	public HotPlaceBoardDto getArticle(int articleNo) throws Exception {
		return hotPlaceBoardRepository.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		hotPlaceBoardRepository.updateHit(articleNo);
	}

	@Override
	public void likeArticle(HotPlaceLikeDto like) throws Exception {

		MembersDto member = membersMapper.findById(like.getUserId())
				.orElseThrow(() -> new NotFoundException("Could not found member id : " + like.getUserId()));

		HotPlaceBoardDto article = hotPlaceBoardRepository.findById(like.getArticleNo())
				.orElseThrow(() -> new NotFoundException("Could not found comment id : " + like.getArticleNo()));


		likeRepository.save(like);
		hotPlaceBoardRepository.likeArticle(article.getArticleNo());
	}

	@Override
	public void unlikeArticle(HotPlaceLikeDto like) throws Exception {
		MembersDto member = membersMapper.findById(like.getUserId())
				.orElseThrow(() -> new NotFoundException("Could not found member id : " + like.getUserId()));

		HotPlaceBoardDto article = hotPlaceBoardRepository.findById(like.getArticleNo())
				.orElseThrow(() -> new NotFoundException("Could not found comment id : " + like.getArticleNo()));

		likeRepository.delete(like);
		hotPlaceBoardRepository.unlikeArticle(article.getArticleNo());
	}

	@Override
	public boolean isLike(HotPlaceLikeDto like) throws Exception {
		MembersDto member = membersMapper.findById(like.getUserId())
				.orElseThrow(() -> new NotFoundException("Could not found member id : " + like.getUserId()));

		HotPlaceBoardDto comment = hotPlaceBoardRepository.findById(like.getArticleNo())
				.orElseThrow(() -> new NotFoundException("Could not found comment id : " + like.getArticleNo()));

		if (!likeRepository.findByMemberAndArticle(member.getUserId(), comment.getArticleNo()).isPresent()){ // 이미 좋아요를 했으면 취소
			return true;
		}
		return false;
	}

	@Override
	public void modifyArticle(HotPlaceBoardDto boardDto) throws Exception {
		// TODO : BoardDaoImpl의 modifyArticle 호출
		hotPlaceBoardRepository.modifyArticle(boardDto);
	}

	@Override
	@Transactional
	public void deleteArticle(int articleNo, String path) throws Exception {
		// TODO : BoardDaoImpl의 deleteArticle 호출
		List<HotPlaceFileInfoDto> fileList = hotPlaceBoardRepository.fileInfoList(articleNo);
		hotPlaceBoardRepository.deleteFile(articleNo);
		hotPlaceBoardRepository.deleteArticle(articleNo);
		for(HotPlaceFileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
	}
}
