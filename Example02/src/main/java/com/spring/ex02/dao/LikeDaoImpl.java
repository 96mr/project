package com.spring.ex02.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.ex02.vo.LikeVO;
import com.spring.ex02.vo.MemberVO;

@Repository
public class LikeDaoImpl implements LikeDao {

	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="com.spring.ex02.mapper.LikeMapper";
	
	@Override
	public void addLike(LikeVO vo) throws Exception {
		sqlSession.insert(namespace+".insertLike", vo);
	}

	@Override
	public void deleteLike(LikeVO vo) throws Exception {
		sqlSession.delete(namespace+".deleteLike", vo);
	}

	
	@Override
	public int isLike(LikeVO vo) throws Exception {
		return sqlSession.selectOne(namespace+".isLike", vo);
	}

	@Override
	public List<MemberVO> boardLiker(int no) throws Exception {
		return sqlSession.selectList(namespace+".selectBoardLiker", no);
	}

}
