package com.greatlearning.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SpringBootSecurityApplication extends SpringBootServletInitializer {
        @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(SpringBootSecurityApplication.class);
	    }

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
		log.info("Its Working...!");
	}

}
