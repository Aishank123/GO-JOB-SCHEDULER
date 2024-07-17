package com.apica.go.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apica.go.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
