package com.spring.ex02.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.ex02.vo.FollowVO;

@Repository
public class FollowDaoImpl implements FollowDao {
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="com.spring.ex02.mapper.FollowMapper";
	@Override
	public List<FollowVO> following(int user_no) throws Exception{
		return sqlSession.selectList(namespace +".following", user_no);
	}
	@Override
	public void addFollow(int m_id, int t_id) throws Exception{
		FollowVO vo = new FollowVO(m_id, t_id);
		vo.setM_id(m_id);
		vo.setT_id(t_id);
		sqlSession.selectList(namespace+".addFollow", vo);
	}
	
	@Override
	public void unFollow(FollowVO vo) throws Exception {
		sqlSession.delete(namespace+".unFollow", vo);
	}
	@Override
	public List<FollowVO> follower(int user_no) throws Exception{
		return sqlSession.selectList(namespace + ".follower", user_no);
	}
	@Override
	public int followCount(int user_no) throws Exception{
		return sqlSession.selectOne(namespace + ".followCount", user_no);
	}
	@Override
	public int followerCount(int user_no) throws Exception{
		return sqlSession.selectOne(namespace + ".followerCount", user_no);
	}
	@Override
	public FollowVO isFollow(FollowVO vo) throws Exception {
		return sqlSession.selectOne(namespace + ".isFollow", vo);
	}

}
