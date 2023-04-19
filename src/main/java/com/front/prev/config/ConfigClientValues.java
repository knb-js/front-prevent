package com.front.prev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class ConfigClientValues {
	
	@Value("${url.backend}")
	private String urlBackend;
	
	@Value("${user.api}")
	private String userApi;
	
	@Value("${password.api}")
	private String passwordApi;

}
