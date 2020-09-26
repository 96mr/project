package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.LikeVO;
import com.spring.ex02.vo.MemberVO;

public interface LikeMapper {
	public void insertLike(LikeVO vo) throws Exception;
	public void deleteLike(LikeVO vo) throws Exception;
	public int isLike(LikeVO vo) throws Exception;
	public List<MemberVO> selectBoardLiker(int no) throws Exception;
}
