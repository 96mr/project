package com.spring.ex02.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.ex02.dao.BoardDao;
import com.spring.ex02.dao.MemberDao;
import com.spring.ex02.dao.NoticeDao;
import com.spring.ex02.dao.ReplyDao;
import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.MemberVO;
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
	public String addComment(String id, ReplyVO vo) throws Exception {
		MemberVO member = memberDao.selectById(id);
		BoardVO board = boardDao.boardDetail(vo.getBno());
		String result = null;
		if(member == null || board == null) {
			return result;
		}
		
		int reply_writer = member.getUser_no();
		vo.setWriter_id(reply_writer);		
		replyDao.addReply(vo);
		
		if(vo.getParent_id() == 0) {	//댓글	
			int board_writer = board.getWriter_id();
			if(reply_writer != board_writer) {
				NoticeVO notice = new NoticeVO(reply_writer, board_writer, "reply");
				notice.setBno(vo.getBno());
				notice.setContent(vo.getContent());
				noticeDao.addNotice(notice);
				
				result = board.getMember().getId();
			}
		}else {		//대댓글	
			ReplyVO reply_parent = replyDao.selectReply(vo.getParent_id());
			int parent_writer = reply_parent.getWriter_id(); //댓글 작성자
			
			if(reply_writer != parent_writer){
				NoticeVO notice = new NoticeVO(reply_writer, parent_writer, "re-reply");
				notice.setBno(vo.getBno());
				notice.setContent(vo.getContent());
				noticeDao.addNotice(notice);
				
				result = reply_parent.getMember().getId();
			}
		}
		return result;
	}

	@Override
	public List<ReplyVO> commentList(int bno) throws Exception {
		return replyDao.selectReplyList(bno);
	}

	@Override
	public int deleteComment(int rep_no) throws Exception {
		replyDao.deleteReply(rep_no);
		return 1;
	}
	
	
}
