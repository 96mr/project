package com.spring.ex02.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.ex02.dao.LikeDao;
import com.spring.ex02.dao.MemberDao;
import com.spring.ex02.dao.NoticeDao;

@Component("echoHandler")
public class EchoHandler extends TextWebSocketHandler {
	
	private static Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>(); //현재 로그인 중인

	@Inject
	private NoticeDao noticeDao;
	
	@Inject
	private MemberDao memberDao;
	
	@Inject 
	private LikeDao likeDao;
	
	public void noticeHeartApplicant(int count) throws Exception{
		Iterator<WebSocketSession> iterator = sessions.iterator();
		
		while(iterator.hasNext()) {
			WebSocketSession session = iterator.next();
			session.sendMessage(new TextMessage(count+""));
		}
		logger.info("{} 보냄",  count);
	}
	
	//서버 접속 성공
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		logger.info("{} 연결됨", session.getId()); 
	}

	//메세지 보냄
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		System.out.println("msg:"+msg);
		//실시간 알람				
		session.sendMessage(new TextMessage((getAlarms(msg)!=0)?getAlarms(msg)+"":"")); 
		
		logger.info("{} 연결, {} 보냄", session.getId(), message.getPayload());
	}

	//접속 해제
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		System.out.println(session.getId());
		logger.info("{} 연결이 끊어짐", session.getId());
	}

	public int getAlarms(String message) throws Exception{
		int id = memberDao.selectById(message).getUser_no();
		int newNotice = noticeDao.newNotice(id);
		return newNotice;
	}
}
