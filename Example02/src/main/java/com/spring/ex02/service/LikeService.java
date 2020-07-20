package com.spring.ex02.service;

import java.util.List;

import com.spring.ex02.vo.MemberVO;

public interface LikeService {
	public String addLike(int bno, String id) throws Exception;
	public void deleteLike(int bno, String id) throws Exception;
	public int isLike(int bno, String id) throws Exception;
	public List<MemberVO> boardLikerList(int no) throws Exception;
}
