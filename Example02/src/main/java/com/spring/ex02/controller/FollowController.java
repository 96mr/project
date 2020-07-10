package com.spring.ex02.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ex02.service.FollowService;
import com.spring.ex02.vo.FollowVO;

/**
 * Handles requests for the application home page.
 */
@RestController
public class FollowController {
	
	private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
	
	@Resource(name="FollowService")
	private FollowService service;

	@RequestMapping(value="/isFollow", method = RequestMethod.POST)
	public int isFollow(@RequestParam("id") String id, HttpSession session) throws Exception{
		String user_id = (String) session.getAttribute("sessionID");
		Boolean result = false;
		if(user_id !=null) 
			result = service.isFollow(user_id, id);
		
		if(result)
			return 1;
		else
			return 0;
	}
	
	@RequestMapping(value="/follow/add", method = RequestMethod.POST)
	public int addFollow(@RequestParam("id") String id, HttpSession session) throws Exception{
		String user_id = (String) session.getAttribute("sessionID");
		System.out.println("m_id:"+user_id+"t_id"+id);
		int result = 0;
		if(user_id !=null) 
			result = service.addFollow(user_id, id);
		return result;
	}
	
	@RequestMapping(value="/follow/delete", method = RequestMethod.POST)
	public int unFollow(@RequestParam("id") String id, HttpSession session) throws Exception{
		String user_id = (String) session.getAttribute("sessionID");
		int result = 0;
		if(user_id !=null) 
			result = service.unFollow(user_id, id);
		return result;
	}
	
	@RequestMapping(value="/following", method = RequestMethod.POST)
	public List<FollowVO> get_Follow(@RequestParam("id") String id) throws Exception{
		List<FollowVO> vo = service.followingById(id);
		return vo;
	}
	
	@ResponseBody
	@RequestMapping(value="/follower", method = RequestMethod.POST)
	public List<FollowVO> get_Follower(@RequestParam("id") String id) throws Exception{
		List<FollowVO> vo = service.followerById(id);
		return vo;
	}
	
	@RequestMapping(value="/follow/count", method = RequestMethod.POST)
	public Map<String,Object> get_FFCount(@RequestParam("id") String id) throws Exception{
		Map<String,Object> map = service.ffCount(id);
		return map;
	}

}
