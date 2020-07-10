package com.spring.ex02.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ex02.common.FileUtils;
import com.spring.ex02.dao.BoardDao;
import com.spring.ex02.dao.LikeDao;
import com.spring.ex02.dao.MemberDao;
import com.spring.ex02.dao.NoticeDao;
import com.spring.ex02.vo.BoardVO;
import com.spring.ex02.vo.FileVO;
import com.spring.ex02.vo.LikeVO;
import com.spring.ex02.vo.NoticeVO;

@Service("BoardService")
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDao boardDao;

	@Inject
	private MemberDao memberDao;
	
	@Inject
	private NoticeDao noticeDao;
	
	@Inject
	private LikeDao likeDao;
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;

	@Override
	@Transactional
	public int boardwrite(String user_id ,BoardVO bvo, MultipartFile[] file) throws Exception {
		int writer = memberDao.selectById(user_id).getUser_no();
		bvo.setWriter_id(writer);
		if(!bvo.getContent().isEmpty() || file.length != 0) {
			int bno = boardDao.write(bvo); // 게시글 작성 + 글 번호 가져옴
			List<FileVO> list = fileUtils.parseFileInfo(bvo.getWriter_id(), file); // 파일리스트
			for (int i = 0; i < list.size(); i++) {
				int fno = boardDao.insertFile(list.get(i)); // 파일 추가s
				boardDao.insertBoardFile(bno, fno);
			}
			return 1;
		}
		return 0;
	}

	@Override
	public Map<String, Object> profileBoard(String profile_id, String id, int tab, int curPage) throws Exception {
		Map<String, Object> result = new HashMap<String,Object>(); 	// 리턴할 결과 map 
		int user_no = memberDao.selectById(profile_id).getUser_no();		// 해당 프로필의 회원 번호
		
		//로그인 여부
		int login_no = 0;
		if(id != null) {
			login_no = memberDao.selectById(id).getUser_no();		
		}
		
		List<BoardVO> list = null;	//게시글 리스트
		if(tab == 3) { 	list = boardDao.likeList(user_no, curPage);}
		else { list = boardDao.boardlist(user_no, tab, curPage); }		//프로필회원 게시글리스트
		for(Iterator<BoardVO> i = list.iterator() ; i.hasNext() ;) {
			BoardVO b = i.next();
			
			if(login_no != 0) {
				if(likeDao.isLike(new LikeVO(b.getBno(), login_no)) == 1) //로그인 시 좋아요 여부
					b.setIslike(1);
			}
		}	
			
		result.put("user", memberDao.ProfileList(user_no));
		result.put("board", list);
		result.put("board_cnt", boardDao.boardCount(user_no));
		return result;
	}
	
	@Override
	public List<BoardVO> timelineList(String id, int page) throws Exception {
		int user_no = memberDao.selectById(id).getUser_no();
		List<BoardVO> list = boardDao.timeLineList(user_no, page);
		for(int i = 0 ; i < list.size();i++) {
			BoardVO b = list.get(i);
			b.setIslike(likeDao.isLike(new LikeVO(b.getBno(), user_no))); //좋아요 했는가?
		}
		return list;
	}

	@Override
	public BoardVO boardDetail(int bno, String id) throws Exception {
		BoardVO b = boardDao.boardDetail(bno);
		if(id != null) {
			int login_no = memberDao.selectById(id).getUser_no();
			b.setIslike(likeDao.isLike(new LikeVO(bno, login_no)));
		}
		return b;
	}

	@Override
	public int boardCount(int id) throws Exception {
		return boardDao.boardCount(id);
	}

	@Override
	public List<BoardVO> searchList(String search_option, String keyword, String id, int curPage) throws Exception {
		int login_no = 0;
		if(id != null) {
			login_no = memberDao.selectById(id).getUser_no();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("page", curPage);
		List<BoardVO> list = boardDao.searchList(map);
		for(int i = 0 ; i < list.size();i++) {
			BoardVO b = list.get(i);
			if(login_no != 0) {
				if(likeDao.isLike(new LikeVO(b.getBno(), login_no)) == 1) //좋아요 했는가?
					b.setIslike(1);
			}
		}
		return list;
	}

	@Override
	public List<NoticeVO> alarmList(String id) throws Exception {
		int user_no = memberDao.selectById(id).getUser_no();
		noticeDao.chkNotice(user_no);
		return noticeDao.noticeList(user_no);
	}

	
}
