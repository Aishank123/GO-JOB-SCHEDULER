package com.apica.go.jobConfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class JobWebSocketHandler extends TextWebSocketHandler {
	private static final List<WebSocketSession> sessions = new ArrayList<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

	public static void broadcast(String message) {
		for (WebSocketSession session : sessions) {
			try {
				session.sendMessage(new TextMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}