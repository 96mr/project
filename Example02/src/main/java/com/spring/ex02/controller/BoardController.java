package com.spring.ex02.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.ex02.service.BoardService;
import com.spring.ex02.service.MemberService;
import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.FileVO;
import com.spring.ex02.vo.ProfileVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Resource(name="BoardService") private BoardService boardService;
	
	@Resource(name="MemberService") private MemberService memberService;
	
	@RequestMapping(value = {"/","/index","/home"}, method = RequestMethod.GET)
	public String home(HttpSession session, Model model){
		logger.info("home :");
		String user_id = (String) session.getAttribute("sessionID");
		if(user_id == null) {
			return "redirect:/browse";
		}
		List<BoardVO> vo;
		try {
			vo = boardService.timelineList(user_id, 0);//회원+팔로우 게시글
			model.addAttribute("timeline", vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	
	//프로필
	@RequestMapping(value = "{user}/profile", method = RequestMethod.GET)
	public String profile(@PathVariable("user") String id, @RequestParam(value="page", defaultValue="0") int page, 
						  HttpSession session, Model model) throws Exception {
		logger.info("profile :"+ id.toString());
		String user_id = (String) session.getAttribute("sessionID");
		
		Map<String, Object> map = boardService.memberProfile(id, user_id, 0, page); //회원의 게시글
		model.addAttribute("user", map.get("user"))
			 .addAttribute("board", map.get("board"))
			 .addAttribute("board_cnt", map.get("board_cnt"));
		return "board/m_board_all";
	}
	@RequestMapping(value = "{id}/profile/{tab}", method = RequestMethod.GET)
	public String profile(@PathVariable("id") String id, @PathVariable("tab") String tab, 
							@RequestParam(value="page", defaultValue="0") int page, 
							HttpSession session, Model model) throws Exception {
		logger.info("profile :"+ tab);
		String user_id = (String) session.getAttribute("sessionID");
		int tab_num = 0;
		
		if(tab.equals("writing"))
			tab_num = 1; 
		else if(tab.equals("media"))
			tab_num = 2;
		else if(tab.equals("likes"))
			tab_num = 3;
		
		Map<String, Object> map = boardService.memberProfile(id, user_id, tab_num, page); 
		
		model.addAttribute("user", map.get("user"))
			 .addAttribute("board", map.get("board"))
			 .addAttribute("board_cnt", map.get("board_cnt"));
		return "board/m_board_"+tab;
	}
	
	@ResponseBody
	@RequestMapping(value="/infiniteBoard", method=RequestMethod.POST)
	public List<BoardVO> infiniteBoard(@RequestParam("page") int page, HttpSession session) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		return boardService.timelineList(id, page);
	}
	
	@ResponseBody
	@RequestMapping(value="/infiniteBoard/profile", method=RequestMethod.POST)
	public Object infiniteBoard(@RequestParam("page") int page, @RequestParam("user") String pid, 
								@RequestParam("tab") int tab, HttpSession session) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		return boardService.memberProfile(pid, id, tab, page).get("board");
	}
	
	@ResponseBody
	@RequestMapping(value="/infiniteBoard/search", method=RequestMethod.POST)
	public List<BoardVO> infiniteBoard(@RequestParam("page") int page,
										@RequestParam(required = false, defaultValue = "content") String option,
										@RequestParam(value="kwd", required=false) String keyword,
										HttpSession session) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		return boardService.searchList(option, keyword, id, page);	
	}
	
	
	@RequestMapping(value="/write", method = RequestMethod.GET)
	public String boardWrite(HttpSession session, Model model) {
		logger.info("write get");
		return "write";
	}
	
	@ResponseBody
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String boardWrite(@RequestParam(value="content", required=false) String content, 
							@RequestParam(value="file", required=false) MultipartFile[] file, 
							HttpSession session, Model model, RedirectAttributes rttr) throws Exception {
		logger.info("write post :"+ content.toString());
		String user_id = (String) session.getAttribute("sessionID");
		if(user_id == null) {
			return "login";
		}
		
		if(content.length()>300) {
			model.addAttribute("fail_msg", "글자수를 초과하였습니다.");
			return "write";
		}
		
		BoardVO vo = new BoardVO();
		vo.setContent(content);						
		boardService.boardwrite(user_id, vo, file);
		
		return "/";
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}/{board_no}/photo", method = RequestMethod.POST)
	public String fullImage(@PathVariable("id") String id, @PathVariable("board_no") int bno, HttpServletRequest request, Model model) throws Exception {
		// 해당 게시글 bno를 이용해서 select해온다
		String user_id = (String) request.getSession().getAttribute("sessionID");
		BoardVO vo = boardService.boardDetail(bno, id, user_id);
		StringBuffer str = new StringBuffer("<div data-bno='"+vo.getBno()+"'><div class='swiper-container' >\n <div class='swiper-wrapper'>");
		
		if(vo != null) {
			for(FileVO file:vo.getFiles()) {
				str.append("<div class='swiper-slide'><div class='full-screen-image'>\n<div class='full-screen-centered'>\n<span>\n");
				str.append("<img src='"+request.getContextPath()+"/resources/images/"+file.getSave_name()+"'/>\n"); 
				str.append("</span>\n</div>\n</div></div>\n");
			}
			str.append("</div>\n");
			if(vo.getFiles().size() > 1)
				str.append("<div class='swiper-button-next'></div>\n<div class='swiper-button-prev'></div>\n");
			str.append("</div>");
			str.append("<div class='full-screen-bottom'>\n <div class='full-screen-icon'>\n");
			str.append("<span onclick=\"likeButton("+vo.getBno()+")\">\n"); 
			str.append("<span class='unlike ");
			if(vo.getIslike() == 0)
				str.append("active");
			str.append("'><i class='far fa-heart fa-2x'></i></span> "); 
			str.append("<span class='like ");
			if(vo.getIslike() == 1)
				str.append("active");
			str.append("'> <i class='fas fa-heart fa-2x'></i></span><br>\n</span>"); 
			str.append("<span class='like-cnt' onclick=\"liker_list("+vo.getBno()+")\">"+vo.getLiker_list().size()+"</span>\n");
			str.append("</div>\n <div class='full-screen-icon'>\n<a href='"+request.getContextPath()+"/"+vo.getMember().getId()+"/"+vo.getBno()+"'>");
			str.append("<span><i class='far fa-comment-alt fa-2x'></i></span><br><span>"+vo.getReply().size()+"</span></a>\n");
			str.append("</div>\n </div></div>");
		}
		return str.toString();
	}
	
	
	@RequestMapping(value = "/{id}/{board_no}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") String id, @PathVariable("board_no") int bno, HttpSession session, Model model) throws Exception {
		// 해당 게시글 bno를 이용해서 select해온다
		logger.info("detail id: "+ id + ", bno:" + bno);
		String user_id = (String) session.getAttribute("sessionID");
		BoardVO vo = boardService.boardDetail(bno, id, user_id);
		model.addAttribute("board",vo);
		return "detail";
	}
	
	//검색 부분
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public String browse(Model model) {	
		logger.info("browse");
		return "browse";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam(required = false, defaultValue = "content") String option, 
						 @RequestParam(value="kwd", required=false) String word, HttpSession session, Model model) throws Exception {
		//검색어로 select한 결과를 list로 가져옴
		logger.info("search :"+ word.toString());
		String login_id = (String) session.getAttribute("sessionID");
		List<BoardVO> vo = boardService.searchList(option, word, login_id, 0);
		model.addAttribute("search_option", option)
			 .addAttribute("keyword", word)
			 .addAttribute("search_result",vo);
		return "search";
	}
	
	@ResponseBody
	@RequestMapping(value = "/board/delete", method = RequestMethod.POST)
	public int deleteBoard(@RequestParam("bno") int bno, HttpSession session) throws Exception{
		logger.info("delete board : " + bno);
		String login_id = (String) session.getAttribute("sessionID");
		if(login_id == null) {
			return -1;
		}
		int result = boardService.deleteBoard(bno, login_id);
		return result;
	}
	
	
	
	@RequestMapping(value = "/edit/profile", method = RequestMethod.GET)
	public String edit_profile(HttpSession session, Model model) throws Exception {
		logger.info("edit profile get");
		String id = (String) session.getAttribute("sessionID");
		ProfileVO vo = memberService.selectProfile(id);
		model.addAttribute("profile", vo);				//회원 프로필 정보를 view에 보냄
		return "edit_profile";
	}
	
	@RequestMapping(value = "/edit/profile", method = RequestMethod.POST)
	public String edit_profile(ProfileVO vo, MultipartFile[] file, HttpSession session, 
								Model model, RedirectAttributes rttr) throws Exception {
		logger.info("edit profile post");
		String id = (String) session.getAttribute("sessionID");
		vo.setUser_no(memberService.selectById(id).getUser_no());
		int edit_result  = memberService.updateProfile(vo, file);	//프로필 업데이트
		
		if(edit_result == 1) {
			rttr.addFlashAttribute("msg","성공적으로 변경되었습니다.");
			return "redirect:/"+id+"/profile";
		}else {
			model.addAttribute("fail_msg", "변경 실패하였습니다");
			return "edit_profile";
		}
	}
	
	@RequestMapping(value = "/alarms", method = RequestMethod.GET)
	public String alarm(HttpSession session, Model model) throws Exception {
		logger.info("alarm");
		if(session.getAttribute("sessionID") == null) {
			return "redirect:/browse";
		}
		String user_id = (String) session.getAttribute("sessionID");
		Map<String,Object> map = boardService.alarmList(user_id);	//회원+팔로우 게시글
		model.addAttribute("alarm_list", map.get("result"))
			 .addAttribute("new_notice_cnt", map.get("new_notice_cnt"));
		return "alarms";
	}
	
}
