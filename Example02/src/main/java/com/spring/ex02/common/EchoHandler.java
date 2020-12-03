package com.spring.ex02.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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
	
	//서버 접속 성공
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		logger.info("{} 연결됨", session.getId()); 
	}

	//메세지 보냄
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message){
		String msg = message.getPayload();
		//실시간 알람	
		Map<String, Object> map = null;
		for (WebSocketSession websession : sessions) {
		         map = websession.getAttributes();
		         String login = (String)map.get("sessionID");
		         //받는사람
		         if (login.equals(msg)) {
		        	 int id;
					try {
						id = memberDao.selectById(msg).getUser_no();
						int newNotice = noticeDao.newNotice(id);
			            websession.sendMessage(new TextMessage((newNotice!=0)?newNotice+"":""));
			        	logger.info("{} 연결, {} 보냄", session.getId(), message.getPayload());
					} catch (NullPointerException e){
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
		  
		         }
		}
	}

	//접속 해제
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		
		logger.info("{} 연결이 끊어짐", session.getId());
	}
}
