package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.LikeVO;
import com.spring.ex02.vo.MemberVO;

public interface LikeMapper {
	public void addLike(LikeVO vo) throws Exception;
	public void deleteLike(LikeVO vo) throws Exception;
	public int isLike(LikeVO vo) throws Exception;
	public List<MemberVO> boardLike(int no) throws Exception;
}
