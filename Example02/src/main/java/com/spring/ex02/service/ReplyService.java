package com.spring.ex02.service;

import java.util.List;

import com.spring.ex02.vo.ReplyVO;

public interface ReplyService {
	public String addComment(String id, ReplyVO vo) throws Exception;
	public List<ReplyVO> commentList(int bno) throws Exception;
	public int deleteComment(int rep_no) throws Exception;
}
