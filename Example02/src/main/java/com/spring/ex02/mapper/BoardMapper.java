package com.spring.ex02.mapper;

import java.util.List;
import java.util.Map;

import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.FileVO;

public interface BoardMapper {
	public int write(BoardVO vo) throws Exception;
	public int insertFile(FileVO vo) throws Exception;
	public void insertBoardFile(Map<String, Integer> map) throws Exception;
	public List<BoardVO> selectBoardList(Map<String, Integer> map) throws Exception;
	public List<BoardVO> selectTimeLineList(Map<String, Integer> map) throws Exception;
	public BoardVO detailBoard(int no) throws Exception;
	public int countBoard(int id) throws Exception;
	public List<BoardVO> likeList(Map<String, Integer> map) throws Exception;
	public List<BoardVO> searchList(Map<String, Object> map) throws Exception;
	public void deleteBoard(int bno) throws Exception;
}
