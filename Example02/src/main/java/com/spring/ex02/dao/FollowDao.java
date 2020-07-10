package com.spring.ex02.dao;

import java.util.List;

import com.spring.ex02.vo.FollowVO;

public interface FollowDao {
	public void addFollow(int m_id, int t_id) throws Exception;
	public void unFollow(FollowVO vo) throws Exception;
	public List<FollowVO> following(int user_no) throws Exception;
	public List<FollowVO> follower(int user_no) throws Exception;
	public int followCount(int user_no) throws Exception;
	public int followerCount(int user_no) throws Exception;
	public FollowVO isFollow(FollowVO vo) throws Exception;
}
