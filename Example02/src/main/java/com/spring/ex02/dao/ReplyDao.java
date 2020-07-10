package com.spring.ex02.dao;

import java.util.List;

import com.spring.ex02.vo.ReplyVO;

public interface ReplyDao {
	public void addReply(ReplyVO vo) throws Exception;
	public void deleteReply(int repno) throws Exception;
	public List<ReplyVO> replyList(int bno) throws Exception;
}
