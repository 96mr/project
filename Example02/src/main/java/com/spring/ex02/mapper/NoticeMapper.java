package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.NoticeVO;

public interface NoticeMapper {
	public void insertNotice(NoticeVO vo) throws Exception;
	public List<NoticeVO> selectNoticeList(int id) throws Exception;
	public int countNewNotice(int id) throws Exception;
	public void updateNoticeChk(int id) throws Exception;
}
