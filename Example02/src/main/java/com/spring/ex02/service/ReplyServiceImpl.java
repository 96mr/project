package com.spring.ex02.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.ex02.dao.BoardDao;
import com.spring.ex02.dao.MemberDao;
import com.spring.ex02.dao.NoticeDao;
import com.spring.ex02.dao.ReplyDao;
import com.spring.ex02.vo.NoticeVO;
import com.spring.ex02.vo.ReplyVO;

@Service("ReplyService")
public class ReplyServiceImpl implements ReplyService {

	@Inject
	private MemberDao memberDao;
	
	@Inject
	private BoardDao boardDao;
	
	@Inject
	private ReplyDao replyDao;

	@Inject
	private NoticeDao noticeDao;
	
	@Override
	@Transactional
	public int addComment(String id, ReplyVO vo) throws Exception {
		int reply_writer = memberDao.selectById(id).getUser_no();
		vo.setWriter_id(reply_writer);		
		replyDao.addReply(vo);
		if(reply_writer != boardDao.boardDetail(vo.getBno()).getWriter_id()) {
			NoticeVO notice = new NoticeVO(reply_writer, boardDao.boardDetail(vo.getBno()).getWriter_id(), "reply");
			notice.setBno(vo.getBno());
			notice.setContent(vo.getContent());
			noticeDao.addNotice(notice);
		}
		return 1;
	}

	@Override
	public List<ReplyVO> commentList(int bno) throws Exception {
		return replyDao.replyList(bno);
	}

	@Override
	public int deleteComment(int rep_no) throws Exception {
		replyDao.deleteReply(rep_no);
		return 1;
	}
	
	
}
