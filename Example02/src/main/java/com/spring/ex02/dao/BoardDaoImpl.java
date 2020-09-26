package com.spring.ex02.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.ex02.mapper.BoardMapper;
import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.FileVO;

@Repository
public class BoardDaoImpl implements BoardDao {
	@Autowired
	private BoardMapper mapper;
	
	@Override
	public List<BoardVO> boardlist(int id, int tab, int page) throws Exception{
		Map<String, Integer> map = new HashMap<>();
		map.put("id", id);
		map.put("tab", tab);
		map.put("page", page);
		return mapper.selectBoardList(map);
	}
	@Override
	public List<BoardVO> timeLineList(int id, int page) throws Exception{
		Map<String, Integer> map = new HashMap<>();
		map.put("id", id);
		map.put("page", page);
		return mapper.selectTimeLineList(map);
	}
	@Override
	public int write(BoardVO vo) throws Exception {
		mapper.write(vo);
		return vo.getBno();
	}
	@Override
	public BoardVO boardDetail(int bno) throws Exception {
		return mapper.detailBoard(bno);
	}
	@Override
	public int insertFile(FileVO vo) throws Exception {
		mapper.insertFile(vo);
		return vo.getFno();
	}
	
	@Override
	public void insertBoardFile(int bno, int fno) throws Exception {
		Map<String, Integer> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fno", fno);
		mapper.insertBoardFile(map);
	}
	@Override
	public int boardCount(int id) throws Exception {
		return mapper.countBoard(id);
	}
	@Override
	public List<BoardVO> searchList(Map<String, Object> map) throws Exception {
		return mapper.searchList(map);
	}
	
	@Override
	public List<BoardVO> likeList(int id, int page) throws Exception {
		Map<String, Integer> map = new HashMap<>();
		map.put("id", id);
		map.put("page", page);
		return mapper.likeList(map);
	}
	
	@Override
	public void deleteBoard(int bno) throws Exception {
		mapper.deleteBoard(bno);
	}
	
}
