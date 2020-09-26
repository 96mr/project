package com.spring.ex02.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.ex02.mapper.ReplyMapper;
import com.spring.ex02.vo.ReplyVO;

@Repository
public class ReplyDaoImpl implements ReplyDao {
	@Autowired
	private ReplyMapper mapper;
	
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		mapper.insertReply(vo);
	}

	@Override
	public void deleteReply(int repno) throws Exception {
		mapper.deleteReply(repno);
	}

	@Override
	public List<ReplyVO> selectReplyList(int bno) throws Exception {
		return mapper.selectReplyList(bno);
	}

	@Override
	public ReplyVO selectReply(int repno) throws Exception {
		return mapper.selectReply(repno);
	}

}
