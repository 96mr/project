package com.spring.ex02.service;

import java.util.List;
import java.util.Map;

import com.spring.ex02.vo.FollowVO;

public interface FollowService {
	public List<FollowVO> followingById(String id) throws Exception;
	public List<FollowVO> followerById(String id) throws Exception;
	public Boolean isFollow(String user_id, String id) throws Exception;
	public int addFollow(String user_id, String id) throws Exception;
	public int unFollow(String user_id, String id) throws Exception;
	public Map<String, Object> ffCount(String id) throws Exception;
}
