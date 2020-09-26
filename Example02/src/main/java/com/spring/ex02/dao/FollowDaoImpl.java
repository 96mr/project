package com.spring.ex02.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.ex02.mapper.FollowMapper;
import com.spring.ex02.vo.FollowVO;

@Repository
public class FollowDaoImpl implements FollowDao {
	@Autowired
	private FollowMapper mapper;
	
	@Override
	public void addFollow(int m_id, int t_id) throws Exception{
		FollowVO vo = new FollowVO(m_id, t_id);
		vo.setM_id(m_id);
		vo.setT_id(t_id);
		mapper.insertFollow(vo);
	}
	@Override
	public void unFollow(FollowVO vo) throws Exception {
		mapper.deleteFollow(vo);
	}
	@Override
	public List<FollowVO> following(int user_no) throws Exception{
		return mapper.selectFollowing(user_no);
	}
	@Override
	public List<FollowVO> follower(int user_no) throws Exception{
		return mapper.selectFollower(user_no);
	}
	@Override
	public int followCount(int user_no) throws Exception{
		return mapper.countFollow(user_no);
	}
	@Override
	public int followerCount(int user_no) throws Exception{
		return mapper.countFollower(user_no);
	}
	@Override
	public FollowVO isFollow(FollowVO vo) throws Exception {
		return mapper.isFollow(vo);
	}

}
