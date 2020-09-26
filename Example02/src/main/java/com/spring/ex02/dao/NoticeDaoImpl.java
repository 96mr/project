package com.spring.ex02.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.ex02.mapper.NoticeMapper;
import com.spring.ex02.vo.NoticeVO;

@Repository
public class NoticeDaoImpl implements NoticeDao {
	@Autowired
	private NoticeMapper mapper;

	@Override
	public void addNotice(NoticeVO vo) throws Exception {
		mapper.insertNotice(vo);
	}

	@Override
	public List<NoticeVO> noticeList(int id) throws Exception {
		return mapper.selectNoticeList(id);
	}

	@Override
	public int newNotice(int id) throws Exception {
		return mapper.countNewNotice(id);
	}

	@Override
	public void chkNotice(int id) throws Exception {
		mapper.updateNoticeChk(id);
	}

}
