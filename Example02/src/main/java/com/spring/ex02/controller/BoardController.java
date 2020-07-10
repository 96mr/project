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
import com.spring.ex02.vo.NoticeVO;
import com.spring.ex02.vo.ProfileVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Resource(name="BoardService")
	private BoardService boardService;
	
	@Resource(name="MemberService")
	private MemberService memberService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) throws Exception {
		if(session.getAttribute("sessionID") == null) {
			return "redirect:/browse";
		}
		String user_id = (String) session.getAttribute("sessionID");
		int page = 0;
		List<BoardVO> vo = boardService.timelineList(user_id, page);	//회원+팔로우 게시글
		model.addAttribute("timeline", vo);
		return "home";
	}
	
	//프로필
	@RequestMapping(value = "{id}/profile", method = RequestMethod.GET)
	public String profile(@PathVariable("id") String id,
							@RequestParam(value="page", defaultValue="0") int page, 
							HttpSession session, Model model) throws Exception {
		String member = (String) session.getAttribute("sessionID");
		Map<String, Object> map = boardService.profileBoard(id, member, 0, page); //회원의 게시글
		model.addAttribute("user", map.get("user"));
		model.addAttribute("board", map.get("board"));
		model.addAttribute("board_cnt", map.get("board_cnt"));
		return "board/m_board_all";
	}
	@RequestMapping(value = "{id}/profile/{tab}", method = RequestMethod.GET)
	public String profile(@PathVariable("id") String id, @PathVariable("tab") String tab, 
							@RequestParam(value="page", defaultValue="0") int page, 
							HttpSession session, Model model) throws Exception {
		String member = (String) session.getAttribute("sessionID");
		Map<String, Object> map = null;
		if(tab.equals("writing"))
			map = boardService.profileBoard(id, member, 1, page); 
		else if(tab.equals("media"))
			map = boardService.profileBoard(id, member, 2, page);
		else if(tab.equals("likes"))
			map = boardService.profileBoard(id, member, 3, page);
		model.addAttribute("user", map.get("user"));
		model.addAttribute("board", map.get("board"));
		model.addAttribute("board_cnt", map.get("board_cnt"));
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
		return boardService.profileBoard(pid, id, tab, page).get("board");
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
		return "write";
	}
	
	@ResponseBody
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String boardWrite(@RequestParam(value="content", required=false) String content, @RequestParam(value="file", required=false) MultipartFile[] file, HttpSession session, Model model) throws Exception {
		String user_id = (String) session.getAttribute("sessionID");
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
		BoardVO vo = boardService.boardDetail(bno, user_id);
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
		String user_id = (String) session.getAttribute("sessionID");
		BoardVO vo = boardService.boardDetail(bno, user_id);
		System.out.println(vo.getReply());
		model.addAttribute("board",vo);
		return "detail";
	}
	
	//검색 부분
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public String browse(Model model) {	
		return "browse";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam(required = false, defaultValue = "content") String option, 
						 @RequestParam(value="kwd", required=false) String word, HttpSession session, Model model) throws Exception {
		//검색어로 select한 결과를 list로 가져옴
		String login_id = (String) session.getAttribute("sessionID");
		int page = 0;
		List<BoardVO> vo = boardService.searchList(option, word, login_id, page);
		model.addAttribute("search_option", option);
		model.addAttribute("keyword", word);
		model.addAttribute("search_result",vo);
		return "search";
	}
	
	@RequestMapping(value = "/edit/profile", method = RequestMethod.GET)
	public String edit_profile(HttpSession session, Model model) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		ProfileVO vo = memberService.selectProfile(id);
		model.addAttribute("profile", vo);				//회원 프로필 정보를 view에 보냄
		return "edit_profile";
	}
	
	@RequestMapping(value = "/edit/profile", method = RequestMethod.POST)
	public String edit_profile(ProfileVO vo, MultipartFile[] file, HttpSession session, RedirectAttributes rttr) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		vo.setUser_no(memberService.selectById(id).getUser_no());
		int edit_result  = memberService.updateProfile(vo, file);	//프로필 업데이트
		
		if(edit_result == 1) {
			rttr.addFlashAttribute("msg","성공적으로 변경되었습니다.");
			return "redirect:/"+id+"/profile";
		}else {
			rttr.addFlashAttribute("msg","변경에 실패하였습니다.");
			return "redirect:/edit/profile";
		}
	}
	
	@RequestMapping(value = "/alarms", method = RequestMethod.GET)
	public String alarm(HttpSession session, Model model) throws Exception {
		if(session.getAttribute("sessionID") == null) {
			return "redirect:/browse";
		}
		String user_id = (String) session.getAttribute("sessionID");
		List<NoticeVO> vo = boardService.alarmList(user_id);	//회원+팔로우 게시글
		model.addAttribute("alarm_list", vo);
		return "alarms";
	}
	
}
