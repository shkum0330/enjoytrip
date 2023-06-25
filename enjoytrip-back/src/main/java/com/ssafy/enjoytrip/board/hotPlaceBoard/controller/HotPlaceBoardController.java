package com.ssafy.enjoytrip.board.hotPlaceBoard.controller;


import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceBoardDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.model.HotPlaceFileInfoDto;
import com.ssafy.enjoytrip.board.hotPlaceBoard.service.HotPlaceBoardService;
import com.ssafy.enjoytrip.board.like.model.HotPlaceCommentLikeDto;
import com.ssafy.enjoytrip.board.like.model.HotPlaceLikeDto;
import com.ssafy.enjoytrip.jwt.JwtTokenProvider;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.members.service.MembersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/hotPlaceBoard")
@Tag(name = "HotPlace", description = "관광지 API Document")
public class HotPlaceBoardController extends HttpServlet {

	@Value("${file.path}")
	private String uploadPath;

	@Value("${file.imgPath}")
	private String uploadImgPath;

	@Autowired
	private HotPlaceBoardService boardService;
	@Autowired
	private MembersService membersService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private ArrayList<String> imgFix = new ArrayList<>(Arrays.asList(new String[]{"jpg", "png"}));

	@GetMapping("/write")
	@Operation(summary = "핫플레이스 글작성 GET", description = "핫플레이스 글작성 페이지로 이동")
	public String write(@RequestParam Map<String, String> map, Model model) {
		log.debug("write call parameter {}", map);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/hotPlaceBoard/write";
	}

	// {"subject":"hi","content":"hello"}
	@PostMapping("/write")
	@Operation(summary = "핫플레이스 글작성 POST", description = "boardDto JSON, 파일을 함께 보냄")
	public HotPlaceBoardDto write(@RequestPart(value="boardDto") HotPlaceBoardDto boardDto,
								  @RequestPart(value="files",required = false) List<MultipartFile> files, HttpServletRequest request,
								  RedirectAttributes redirectAttributes) throws Exception {
		String token=jwtTokenProvider.resolveToken(request,"Access");
		if(!jwtTokenProvider.validateToken(token)){
			throw new RuntimeException("유효하지 않은 토큰입니다.");
		}
		String userId= jwtTokenProvider.getUserId(token);
		String userName=membersService.findById(userId).get().getUsername();
		boardDto.setUserId(userId);
		boardDto.setUserName(userName);
		log.debug("로그인 유저: {}",membersService.findById(userId));
		log.debug("write boardDto : {}", boardDto);
//		FileUpload 관련 설정.
		log.debug("MultipartFile.isEmpty : {}", files==null);
		if (files!=null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date()); // 날짜 폴더 지정
			String saveFolder = uploadPath + File.separator + today;
			log.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<HotPlaceFileInfoDto> fileInfos = new ArrayList<HotPlaceFileInfoDto>();
			for (MultipartFile mfile : files) {
				HotPlaceFileInfoDto fileInfoDto = new HotPlaceFileInfoDto();

				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					log.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			boardDto.setFileInfos(fileInfos);
		}

		boardService.writeArticle(boardDto);
		log.info("게시판 글 객체 = {}",boardDto);
		return boardDto;
	}

	@GetMapping("/{articleNo}/findById")
	@Operation(summary = "ID로 게시물 찾기 ", description = "PathVariable로 페이지번호 필요")
	public Optional<HotPlaceBoardDto> findById(@PathVariable int articleNo) throws Exception {
		return boardService.findById(articleNo);
	}

	@GetMapping("/list")
	@Operation(summary = "핫플레이스 게시물 리스트", description = "게시물 리스트 조회")
	public List<HotPlaceBoardDto> list(Map<String, String> map) throws Exception {
		log.debug("list parameter pgno : {}", map.get("pgno"));
		ModelAndView mav = new ModelAndView();
		List<HotPlaceBoardDto> list = boardService.listArticle();
//		PageNavigation pageNavigation = boardService.makePageNavigation(map);
//		mav.addObject("articles", list);
//		mav.addObject("navigation", pageNavigation);
//		mav.addObject("pgno", map.get("pgno"));
//		mav.addObject("key", map.get("key"));
//		mav.addObject("word", map.get("word"));
//		mav.setViewName("board/hotPlaceBoard/list");
		return list;
	}
	@GetMapping("/list/findBySubject")
	@Operation(summary = "제목으로 게시글 조회 GET", description = "파라미터 형식: subject=??")
	List<HotPlaceBoardDto> findBySubject(@RequestParam String subject) throws Exception{
		return boardService.findBySubject(subject);
	}

	@GetMapping("/list/findByContent")
	@Operation(summary = "내용으로 게시글 조회 GET", description = "파라미터 형식: content=??")
	List<HotPlaceBoardDto> findByContent(@RequestParam String content) throws Exception{
		return boardService.findByContent(content);
	}
	@GetMapping("/list/findByWriter")
	@Operation(summary = "글쓴이로 게시글 조회 GET", description = "파라미터 형식: userId=??")
	List<HotPlaceBoardDto> findByWriter(@RequestParam String userId) throws Exception{
		return boardService.findByWriter(userId);
	}
	@GetMapping("/view/{articleno}")
	@Operation(summary = "핫플레이스 게시물 조회", description = "path로 게시글번호 필요")
	public HotPlaceBoardDto view(@PathVariable("articleno") int articleNo,Map<String, String> map, HttpServletRequest request,Model model)
			throws Exception {
		log.debug("view articleNo : {}", articleNo);
		HotPlaceBoardDto boardDto = boardService.getArticle(articleNo);

		boardService.updateHit(articleNo);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));

		List<String> imgList = new ArrayList<>();
		List<HotPlaceFileInfoDto> fileList = boardDto.getFileInfos();

		for (int i = 0; i < fileList.size(); i++) {
			HotPlaceFileInfoDto cur = fileList.get(i);
			String[] splts = cur.getSaveFile().split("\\.");

			if(imgFix.contains(splts[1])){
				imgList.add(cur.getSaveFolder()+"/"+cur.getSaveFile());
			}
		}
		model.addAttribute("imgList",imgList);

		return boardDto;
	}

	@GetMapping("/modify/{articleno}")
	public String modify(@RequestParam("articleno") int articleNo ,Map<String, String> map, Model model)
			throws Exception {
		log.debug("modify articleNo : {}", articleNo);
		HotPlaceBoardDto boardDto = boardService.getArticle(articleNo);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/hotPlaceBoard/modify";
	}

	@PostMapping("/modify/{articleno}")
	@Operation(summary = "핫플레이스 게시물 수정", description = "PathVariable로 페이지번호 필요")
	public String modify(@RequestPart(value="boardDto") HotPlaceBoardDto boardDto,
						 @RequestPart(value="files",required = false) List<MultipartFile> files,@PathVariable("articleno") int articleNo, HttpServletRequest request) throws Exception {
		check(request,articleNo,"수정");
		//		FileUpload 관련 설정.
		log.debug("MultipartFile.isEmpty : {}", files==null);
		if (files!=null) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date()); // 날짜 폴더 지정
			String saveFolder = uploadPath + File.separator + today;
			log.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<HotPlaceFileInfoDto> fileInfos = new ArrayList<HotPlaceFileInfoDto>();
			for (MultipartFile mfile : files) {
				HotPlaceFileInfoDto fileInfoDto = new HotPlaceFileInfoDto();

				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					log.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			boardDto.setFileInfos(fileInfos);
		}
		log.debug("modify boardDto : {}", boardDto);
		boardDto.setArticleNo(articleNo);
		boardService.modifyArticle(boardDto);

		return "게시물 수정 완료";
	}

	@GetMapping("/delete/{articleno}")
	@Operation(summary = "핫플레이스 게시물 삭제", description = "PathVariable로 페이지번호 필요")
	public String delete(@PathVariable("articleno") int articleNo,Map<String, String> map,HttpServletRequest request,
						 RedirectAttributes redirectAttributes) throws Exception {
		check(request,articleNo,"삭제");
		log.debug("delete articleNo : {}", articleNo);
		boardService.deleteArticle(articleNo, uploadPath);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "게시물 삭제 완료";
	}

	@GetMapping("/download")
	@Operation(summary = "파일 다운로드", description = "파일 다운로드")
	public HotPlaceFileInfoDto downloadFile(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
									 @RequestParam("sfile") String sfile) {
//		HotPlaceFileInfoDto fileInfo = new HotPlaceFileInfoDto(sfolder,ofile,sfile);
//		fileInfo.put("sfolder", sfolder);
//		fileInfo.put("ofile", ofile);
//		fileInfo.put("sfile", sfile);
		return new HotPlaceFileInfoDto(sfolder,ofile,sfile);
	}
	@GetMapping("/like/{articleNo}")
	@Operation(summary = "게시글 좋아요 GET", description = "로그인해야만 좋아요 가능. 1번 요청하면 좋아요(true), 2번 요청하면 취소(false)")
	public boolean likeHotPlaceComment(@PathVariable int articleNo, HttpServletRequest request) throws Exception {
		String token=jwtTokenProvider.resolveToken(request,"Access");
		if(!jwtTokenProvider.validateToken(token)){
			throw new RuntimeException("유효하지 않은 토큰입니다.");
		}
		String userId= jwtTokenProvider.getUserId(token);
		Optional<MembersDto> login=membersService.findById(userId);
		log.info("로그인 유저: {}",login);
		log.info("findById로 찾은 글쓴이: {}",boardService.findById(articleNo).get().getUserId());
		if(login==null){
			throw new RuntimeException("로그인하세요.");
		}

		HotPlaceLikeDto dto=new HotPlaceLikeDto(userId,articleNo);
		log.info("컨트롤러 HotPlaceLikeDto={}",dto);
		log.info("컨트롤러 userId={}",dto.getUserId());
		if(boardService.isLike(dto)){
			log.info("좋아요");
			boardService.likeArticle(dto);
			return true;
		}
		else {
			log.info("좋아요 취소");
			boardService.unlikeArticle(dto);
			return false;
		}
	}
	private void check(HttpServletRequest request,int articleNo,String msg) throws Exception {
		String token=jwtTokenProvider.resolveToken(request,"Access");
		if(!jwtTokenProvider.validateToken(token)){
			throw new RuntimeException("유효하지 않은 토큰입니다.");
		}
		String userId= jwtTokenProvider.getUserId(token);
		Optional<MembersDto> login=membersService.findById(userId);
		log.info("로그인 유저: {}",login);
		log.info("findById로 찾은 글쓴이: {}",boardService.findById(articleNo).get().getUserId());
		if(login==null){
			throw new RuntimeException("로그인하세요.");
		}
		if(!boardService.findById(articleNo).get().getUserId().equals(login.get().getUserId())){ // 다른 아이디로 댓글을 수정,삭제하려 할 때
			throw new RuntimeException("자신이 쓴 게시글만 " +msg+"할 수 있습니다.");
		}
	}
}
