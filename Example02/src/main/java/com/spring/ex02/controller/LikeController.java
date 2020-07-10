package com.spring.ex02.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ex02.service.LikeService;
import com.spring.ex02.vo.MemberVO;

/**
 * Handles requests for the application home page.
 */
@RestController
public class LikeController {
	
	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
	

	@Resource(name="LikeService")
	private LikeService likeService;
	
	
	@RequestMapping(value = "/like/add", method = RequestMethod.POST)
	public int addLike(@RequestParam("no") int bno, HttpSession session, Model model) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		int result = 0;
		if(id != null) {
			likeService.addLike(bno, id);
			result = 1;
		}
		return result;
	}
	
	@RequestMapping(value = "/like/delete", method = RequestMethod.POST)
	public int deleteLike(@RequestParam("no") int bno, HttpSession session, Model model) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		int result = 0;
		if(id != null) {
			likeService.deleteLike(bno, id);
			result = 1;
		}
		return result;
	}
	
	@RequestMapping(value = "/islike/{no}", method = RequestMethod.POST)
	public int isLike(@PathVariable("no") int bno, HttpSession session, Model model) throws Exception {
		String id = (String) session.getAttribute("sessionID");
		int result = 0;
		if(id != null) {
			result = likeService.isLike(bno, id);
		}
		return result;
	}
	
	@RequestMapping(value = "/likes/{no}", method = RequestMethod.POST)
	public List<MemberVO> boardLike(@PathVariable("no") int bno, Model model) throws Exception {
		List<MemberVO> vo = likeService.boardLike(bno);
		return vo;
	}
}
