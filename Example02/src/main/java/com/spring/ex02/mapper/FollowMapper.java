package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.FollowVO;
import com.spring.ex02.vo.MemberVO;

public interface FollowMapper {
	public List<MemberVO> following(String id) throws Exception;
	public void addFollow(FollowVO vo) throws Exception;
	public void unFollow(FollowVO vo) throws Exception;
	public List<FollowVO> follower(int user_no) throws Exception;
	public int followCount(int user_no) throws Exception;
	public int followerCount(int user_no) throws Exception;
	public FollowVO isFollow(FollowVO vo) throws Exception;
}
