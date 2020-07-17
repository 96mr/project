package com.spring.ex02.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.FileVO;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="com.spring.ex02.mapper.BoardMapper";
	
	@Override
	public List<BoardVO> boardlist(int id, int tab, int page) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("tab", tab);
		map.put("page", page);
		return sqlSession.selectList(namespace+".boardList", map);
	}
	@Override
	public List<BoardVO> timeLineList(int id, int page) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("page", page);
		return sqlSession.selectList(namespace+".timeLineList", map);
	}
	@Override
	public int write(BoardVO vo) throws Exception {
		sqlSession.insert(namespace+".write",vo);
		return vo.getBno();
	}
	@Override
	public BoardVO boardDetail(int bno) throws Exception {
		return sqlSession.selectOne(namespace+".detailBoard", bno);
	}
	@Override
	public int insertFile(FileVO fileVO) throws Exception {
		sqlSession.insert(namespace+".insertFile",fileVO);
		return fileVO.getFno();
	}
	
	@Override
	public void insertBoardFile(int bno, int fno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fno", fno);
		sqlSession.insert(namespace+".insertBoardFile", map);
	}
	@Override
	public int boardCount(int id) throws Exception {
		return sqlSession.selectOne(namespace+".boardCount", id);
	}
	@Override
	public List<BoardVO> searchList(Map<String, Object> map) throws Exception {
		return sqlSession.selectList(namespace+".searchList", map);
	}
	
	@Override
	public List<BoardVO> likeList(int id, int page) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("page", page);
		return sqlSession.selectList(namespace+".likeList", map);
	}
	
	@Override
	public void deleteBoard(int bno) throws Exception {
		sqlSession.update(namespace+".deleteBoard", bno);
	}
	
}
