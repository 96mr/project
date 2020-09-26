package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.FollowVO;

public interface FollowMapper {
	public void insertFollow(FollowVO vo) throws Exception;
	public void deleteFollow(FollowVO vo) throws Exception;
	public List<FollowVO> selectFollowing(int user_no) throws Exception;
	public List<FollowVO> selectFollower(int user_no) throws Exception;
	public int countFollow(int user_no) throws Exception;
	public int countFollower(int user_no) throws Exception;
	public FollowVO isFollow(FollowVO vo) throws Exception;
}
