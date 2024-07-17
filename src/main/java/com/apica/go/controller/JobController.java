package com.apica.go.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apica.go.entity.Job;
import com.apica.go.service.JobService;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "http://localhost:4200")
public class JobController {
	@Autowired
	private JobService jobService;

	@MessageMapping("/job")
	@SendTo("/topic/jobs")
	public Job handleJob(Job job) {
		Job savedJob = null;
		try {
			savedJob = jobService.submitJob(job.getName(), job.getDuration().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedJob;

	}

	@PostMapping
	public Job submitJob(@RequestBody Job jobRequest) {
		Job job = null;
		try {
			job = jobService.submitJob(jobRequest.getName(), jobRequest.getDuration().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return job;
	}

	@GetMapping
	public ResponseEntity<List<Job>> getJobs() {
		return ResponseEntity.ok(jobService.getJobs());
	}

}