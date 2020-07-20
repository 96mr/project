package com.spring.ex02.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ex02.service.ReplyService;
import com.spring.ex02.vo.ReplyVO;

/**
 * Handles requests for the application home page.
 */
@RestController
public class ReplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Resource(name="ReplyService")
	private ReplyService service;

	@RequestMapping(value="/comment/add", method = RequestMethod.POST)
	public String addComment(ReplyVO vo, HttpSession session) throws Exception{
		logger.info("add comment");
		String id = (String)session.getAttribute("sessionID");
		String result = null;
		if(id != null) {
			result = service.addComment(id, vo);
		}
		return result;
	}
	
	@RequestMapping(value="/comment/delete", method = RequestMethod.POST)
	public int deleteComment(int rep_no, HttpSession session) throws Exception{
		logger.info("delete Comment :"+ rep_no);
		
		String id = (String)session.getAttribute("sessionID");
		int result = 0;
		
		if(id != null) {
			result = service.deleteComment(rep_no);
		}
		return result;
	}

	@RequestMapping(value="/comment/list", method = RequestMethod.POST)
	public List<ReplyVO> commentList(int bno, HttpSession session) throws Exception{
		logger.info("commentList :" + bno);
		return service.commentList(bno);
	}
	
}
