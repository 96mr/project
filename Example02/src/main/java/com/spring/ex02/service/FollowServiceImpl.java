package com.spring.ex02.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.ex02.dao.FollowDao;
import com.spring.ex02.dao.MemberDao;
import com.spring.ex02.dao.NoticeDao;
import com.spring.ex02.vo.FollowVO;
import com.spring.ex02.vo.MemberVO;
import com.spring.ex02.vo.NoticeVO;

@Service("FollowService")
public class FollowServiceImpl implements FollowService {
	
	@Inject
	private FollowDao followdao;
	
	@Inject
	private MemberDao memberdao;
	
	@Inject NoticeDao noticedao;
	
	@Override
	public List<FollowVO> followingById(String id) throws Exception{	//팔로잉 목록
		List<FollowVO> result = new ArrayList<FollowVO>();
		MemberVO vo = memberdao.selectById(id);
		if(vo == null) {
			return result;
		}
		int user_no = vo.getUser_no();
		return result = followdao.following(user_no);
	}
	
	@Override
	public List<FollowVO> followerById(String id) throws Exception{		//팔로워 목록
		List<FollowVO> result = new ArrayList<FollowVO>();
		MemberVO vo = memberdao.selectById(id);
		if(vo != null) {
			int user_no = vo.getUser_no();
			result = followdao.follower(user_no);
		}
		return result;
	}
	
	
	@Override
	public Boolean isFollow(String user_id, String id) throws Exception {	//팔로잉 중인가?
		MemberVO m_vo = memberdao.selectById(user_id);
		MemberVO t_vo = memberdao.selectById(id);
		if(m_vo == null || t_vo == null) {
			return false;
		}
		int m_id = m_vo.getUser_no();
		int t_id = t_vo.getUser_no();
		
		FollowVO result = followdao.isFollow(new FollowVO(m_id, t_id));
		if(result == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public int addFollow(String user_id, String id) throws Exception{
		MemberVO m_vo = memberdao.selectById(user_id);
		MemberVO t_vo = memberdao.selectById(id);
		if(m_vo == null || t_vo == null) {
			return 0;
		}
		int m_id = m_vo.getUser_no();
		int t_id = t_vo.getUser_no();
		FollowVO result = followdao.isFollow(new FollowVO(m_id, t_id));
		if(result == null) {
			if(m_id != t_id) {
				NoticeVO notice = new NoticeVO(m_id, t_id, "follow");
				noticedao.addNotice(notice);
				followdao.addFollow(m_id, t_id);
			}
			return 1;
		}else {
			return 0;
		}
	}
	
	
	@Override
	@Transactional
	public int unFollow(String user_id, String id) throws Exception {
		MemberVO m_vo = memberdao.selectById(user_id);
		MemberVO t_vo = memberdao.selectById(id);
		if(m_vo == null || t_vo == null) {
			return 0;
		}
		int m_id = m_vo.getUser_no();
		int t_id = t_vo.getUser_no();
		FollowVO vo = new FollowVO(m_id, t_id);
		FollowVO result = followdao.isFollow(vo);
		if(result != null) {
			followdao.unFollow(vo);
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public Map<String, Object> ffCount(String id) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		MemberVO vo = memberdao.selectById(id);
		int user_no;
		if(vo == null) {
			user_no = 0;
		}else {
			user_no = vo.getUser_no();
		}
		map.put("follow", followdao.followCount(user_no));
		map.put("follower", followdao.followerCount(user_no));
		return map;
	}

}
