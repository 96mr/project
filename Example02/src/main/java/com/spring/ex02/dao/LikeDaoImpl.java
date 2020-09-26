package com.spring.ex02.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.ex02.mapper.LikeMapper;
import com.spring.ex02.vo.LikeVO;
import com.spring.ex02.vo.MemberVO;

@Repository
public class LikeDaoImpl implements LikeDao {
	@Autowired
	private LikeMapper mapper;
	
	@Override
	public void addLike(LikeVO vo) throws Exception {
		mapper.insertLike(vo);
	}

	@Override
	public void deleteLike(LikeVO vo) throws Exception {
		mapper.deleteLike(vo);
	}

	@Override
	public int isLike(LikeVO vo) throws Exception {
		return mapper.isLike(vo);
	}

	@Override
	public List<MemberVO> boardLiker(int no) throws Exception {
		return mapper.selectBoardLiker(no);
	}

}
