package com.spring.ex02.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.ex02.vo.ReplyVO;

@Repository
public class ReplyDaoImpl implements ReplyDao {
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="com.spring.ex02.mapper.ReplyMapper";
	
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		sqlSession.insert(namespace+".insertReply", vo);
	}

	@Override
	public void deleteReply(int repno) throws Exception {
		sqlSession.delete(namespace+".deleteReply", repno);
	}

	@Override
	public List<ReplyVO> selectReplyList(int bno) throws Exception {
		return sqlSession.selectList(namespace+".selectReplyList", bno);
	}

	@Override
	public ReplyVO selectReply(int repno) throws Exception {
		return sqlSession.selectOne(namespace+".selectReply",repno);
	}

}
