package com.ssafy.enjoytrip.board.tripInfoBoard.controller;


import com.ssafy.enjoytrip.board.tripInfoBoard.model.tripInfoBoardDto;
import com.ssafy.enjoytrip.board.tripInfoBoard.model.tripInfoFileInfoDto;
import com.ssafy.enjoytrip.board.tripInfoBoard.service.tripInfoBoardService;
import com.ssafy.enjoytrip.members.model.MembersDto;
import com.ssafy.enjoytrip.util.PageNavigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/tripInfoBoard")
public class tripInfoBoardController extends HttpServlet {
	private final Logger logger = LoggerFactory.getLogger(tripInfoBoardController.class);

	@Value("${file.path}")
	private String uploadPath;

	@Value("${file.imgPath}")
	private String uploadImgPath;

	private tripInfoBoardService boardService;
	@Autowired
	public tripInfoBoardController(tripInfoBoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping("/write")
	public String write(@RequestParam Map<String, String> map, Model model) {
		logger.debug("write call parameter {}", map);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/tripInfoBoard/write";
	}

	@PostMapping("/write")
	public String write(tripInfoBoardDto boardDto, @RequestParam("upfile") MultipartFile[] files, HttpSession session,
						RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("write boardDto : {}", boardDto);
		MembersDto memberDto = (MembersDto) session.getAttribute("login");
		boardDto.setUserId(memberDto.getUserId());

//		FileUpload 관련 설정.
		logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
		if (!files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + File.separator + today;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<tripInfoFileInfoDto> fileInfos = new ArrayList<tripInfoFileInfoDto>();
			for (MultipartFile mfile : files) {
				tripInfoFileInfoDto fileInfoDto = new tripInfoFileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			boardDto.setFileInfos(fileInfos);
		}

		boardService.writeArticle(boardDto);
		redirectAttributes.addAttribute("pgno", "1");
		redirectAttributes.addAttribute("key", "");
		redirectAttributes.addAttribute("word", "");
		return "redirect:/tripInfoBoard/list";
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
		logger.debug("list parameter pgno : {}", map.get("pgno"));
		ModelAndView mav = new ModelAndView();
		List<tripInfoBoardDto> list = boardService.listArticle(map);
		PageNavigation pageNavigation = boardService.makePageNavigation(map);
		mav.addObject("articles", list);
		mav.addObject("navigation", pageNavigation);
		mav.addObject("pgno", map.get("pgno"));
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("board/tripInfoBoard/list");
		return mav;
	}

	@GetMapping("/view")
	public String view(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
			throws Exception {

		logger.debug("view articleNo : {}", articleNo);
		tripInfoBoardDto boardDto = boardService.getArticle(articleNo);
		boardService.updateHit(articleNo);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/tripInfoBoard/view";
	}

	@GetMapping("/modify")
	public String modify(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
			throws Exception {
		logger.debug("modify articleNo : {}", articleNo);
		tripInfoBoardDto boardDto = boardService.getArticle(articleNo);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/tripInfoBoard/modify";
	}

	@PostMapping("/modify")
	public String modify(tripInfoBoardDto boardDto, @RequestParam Map<String, String> map,
						 RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("modify boardDto : {}", boardDto);
		boardService.modifyArticle(boardDto);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/tripInfoBoard/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map,
						 RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("delete articleNo : {}", articleNo);
		boardService.deleteArticle(articleNo, uploadPath);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/tripInfoBoard/list";
	}

	@GetMapping("/download")
	public ModelAndView downloadFile(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
									 @RequestParam("sfile") String sfile) {
		Map<String, Object> fileInfo = new HashMap<String, Object>();
		fileInfo.put("sfolder", sfolder);
		fileInfo.put("ofile", ofile);
		fileInfo.put("sfile", sfile);
		return new ModelAndView("fileDownLoadView", "downloadFile", fileInfo);
	}


}
