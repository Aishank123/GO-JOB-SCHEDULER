package com.apica.go.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apica.go.entity.Job;
import com.apica.go.jobConfig.JobWebSocketHandler;
import com.apica.go.service.JobService;

import jakarta.annotation.PostConstruct;

@Component
public class JobEventListener {
	@Autowired
	private JobService jobService;

	@PostConstruct
	public void init() {
		new Thread(this::processJobs).start();
	}

	private void processJobs() {
		while (true) {
			try {
				Job job = jobService.getNextJob();
				if (job != null) {
					jobService.updateJobStatus(job.getId(), "running");
					JobWebSocketHandler.broadcast("Job " + job.getName() + " is running");
					Thread.sleep(job.getDuration().toMillis());
					jobService.updateJobStatus(job.getId(), "completed");
					JobWebSocketHandler.broadcast("Job " + job.getName() + " is completed");
				} else {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
