package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.NoticeVO;

public interface NoticeMapper {
	public void addNotice(NoticeVO vo) throws Exception;
	public List<NoticeVO> noticeList(int id) throws Exception;
	public int newNotice(int id) throws Exception;
	public void chkNotice(int id) throws Exception;
}
