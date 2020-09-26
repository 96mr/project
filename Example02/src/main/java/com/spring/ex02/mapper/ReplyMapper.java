package com.spring.ex02.mapper;

import java.util.List;

import com.spring.ex02.vo.ReplyVO;

public interface ReplyMapper {
	public void insertReply(ReplyVO vo) throws Exception;
	public void deleteReply(int repno) throws Exception;
	public List<ReplyVO> selectReplyList(int bno) throws Exception;
	public ReplyVO selectReply(int repno) throws Exception;
}
