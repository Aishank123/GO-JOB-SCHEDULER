package com.apica.go.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apica.go.controller.WebSocketController;
import com.apica.go.entity.Job;
import com.apica.go.repository.JobRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

@Service
public class JobService {
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private WebSocketController webSocketController;

	private final PriorityQueue<Job> jobQueue = new PriorityQueue<>(
			(j1, j2) -> j1.getDuration().compareTo(j2.getDuration()));

	public synchronized Job submitJob(String name, String durationStr) {
		try {
			Duration duration = Duration.parse(durationStr);

			Job job = new Job(name, duration, "pending");
			jobQueue.add(job);
			jobRepository.save(job);
			webSocketController.broadcastJobUpdate(job);
			return job;
		} catch (Exception e) {
			throw new RuntimeException("Invalid duration format: " + durationStr, e);
		}
	}

	public List<Job> getJobs() {
		return jobRepository.findAll();
	}

	public synchronized void updateJobStatus(Long jobId, String status) {
		Optional<Job> jobOptional = jobRepository.findById(jobId);
		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setStatus(status);
			jobRepository.save(job);
			webSocketController.broadcastJobUpdate(job);
		}
	}

	public synchronized Job getNextJob() {
		return jobQueue.poll();
	}

}