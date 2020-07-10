package com.spring.ex02.dao;

import java.util.List;
import java.util.Map;

import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.FileVO;

public interface BoardDao {
	public int write(BoardVO vo) throws Exception;
	public List<BoardVO> boardlist(int id, int tab, int page) throws Exception;
	public List<BoardVO> timeLineList(int id, int page) throws Exception;
	public BoardVO boardDetail(int bno) throws Exception;
	public int insertFile(FileVO fileVO) throws Exception;
	public void insertBoardFile(int bno, int fno) throws Exception;
	public int boardCount(int id) throws Exception;
	public List<BoardVO> searchList(Map<String, Object> map) throws Exception;
	public List<BoardVO> likeList(int id, int page) throws Exception;
}
