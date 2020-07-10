package com.spring.ex02.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.ex02.vo.NoticeVO;

@Repository
public class NoticeDaoImpl implements NoticeDao {
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="com.spring.ex02.mapper.NoticeMapper";

	@Override
	public void addNotice(NoticeVO vo) throws Exception {
		sqlSession.insert(namespace+".addNotice", vo);
	}

	@Override
	public List<NoticeVO> noticeList(int id) throws Exception {
		return sqlSession.selectList(namespace+".noticeList", id);
	}

	@Override
	public int newNotice(int id) throws Exception {
		return sqlSession.selectOne(namespace+".newNotice", id);
	}

	@Override
	public void chkNotice(int id) throws Exception {
		sqlSession.update(namespace+".chkNotice", id);
	}

}
