package com.apica.go.jobConfig;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class WebConfig {

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*").allowCredentials(true).maxAge(3600);
	}

}
