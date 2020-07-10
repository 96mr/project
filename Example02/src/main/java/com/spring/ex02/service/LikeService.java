package com.spring.ex02.service;

import java.util.List;

import com.spring.ex02.vo.LikeVO;
import com.spring.ex02.vo.MemberVO;

public interface LikeService {
	public void addLike(int bno, String id) throws Exception;
	public void deleteLike(int bno, String id) throws Exception;
	public int isLike(int bno, String id) throws Exception;
	public List<MemberVO> boardLike(int no) throws Exception;
}
