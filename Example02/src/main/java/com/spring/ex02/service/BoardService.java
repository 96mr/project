package com.spring.ex02.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.NoticeVO;

public interface BoardService {
	public int boardwrite(String user_id, BoardVO vo, MultipartFile[] file) throws Exception;
	public Map<String, Object> memberProfile(String profile_id, String id, int tab, int curPage) throws Exception;
	public List<BoardVO> timelineList(String id, int page) throws Exception;
	public BoardVO boardDetail(int bno, String id) throws Exception;
	public int boardCount(int id) throws Exception;
	public List<BoardVO> searchList(String search_option, String keyword, String id, int curPage) throws Exception;
	public List<NoticeVO> alarmList(String id) throws Exception;
}
