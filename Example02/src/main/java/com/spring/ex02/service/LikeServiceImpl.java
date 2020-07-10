package com.spring.ex02.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.ex02.dao.BoardDao;
import com.spring.ex02.dao.LikeDao;
import com.spring.ex02.dao.MemberDao;
import com.spring.ex02.dao.NoticeDao;
import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.LikeVO;
import com.spring.ex02.vo.MemberVO;
import com.spring.ex02.vo.NoticeVO;

@Service("LikeService")
public class LikeServiceImpl implements LikeService {

	@Inject
	private LikeDao likeDao;
	
	@Inject
	private BoardDao boardDao;
	
	@Inject
	private MemberDao memberDao;
	
	@Inject
	private NoticeDao noticeDao;
	
	@Override
	@Transactional
	public void addLike(int bno, String id) throws Exception {
		BoardVO board= boardDao.boardDetail(bno);
		MemberVO member = memberDao.selectById(id);
		if(member != null && board != null) {
			int liker_id = member.getUser_no();
			LikeVO vo = new LikeVO(bno, liker_id);
			if(likeDao.isLike(vo) != 1) { 
				likeDao.addLike(vo);
				if(board.getWriter_id() != liker_id) { //작성자와 좋아요 누른 사람이 일치하지 않으면 -> 좋아요 알림
					NoticeVO notice = new NoticeVO(liker_id, board.getWriter_id(), "like");
					notice.setBno(vo.getBno());
					noticeDao.addNotice(notice);
				}
			}
		}
	}

	@Override
	@Transactional
	public void deleteLike(int bno, String id) throws Exception {
		BoardVO board= boardDao.boardDetail(bno);
		MemberVO member = memberDao.selectById(id);
		if(member != null && board != null) {
			int like_id = member.getUser_no();
			LikeVO vo = new LikeVO(bno, like_id);
			likeDao.deleteLike(vo);
		}
		
	}

	@Override
	public int isLike(int bno, String id) throws Exception {
		BoardVO board= boardDao.boardDetail(bno);
		MemberVO member = memberDao.selectById(id);
		if(member != null && board != null) {
			int like_id = member.getUser_no();
			LikeVO vo = new LikeVO(bno, like_id);
			return likeDao.isLike(vo);
		}else {
			return 0;
		}
	}

	@Override
	public List<MemberVO> boardLike(int no) throws Exception{
		return likeDao.boardLike(no);
	}

}
