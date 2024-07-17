package com.apica.go.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.apica.go.entity.Job;

@RestController
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate template;

	public void broadcastJobUpdate(Job job) {
		template.convertAndSend("/topic/jobs", job);
	}

}
