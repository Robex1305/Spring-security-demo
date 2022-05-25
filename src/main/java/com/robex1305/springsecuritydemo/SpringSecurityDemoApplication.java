package com.robex1305.springsecuritydemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringSecurityDemoApplication {


	@Value("${application.name}")
	private String applicationName;

	@PostConstruct
	public void init(){
		System.out.println("----------------------------------------------");
		System.out.println("Application \"" + applicationName + "\" loaded");
		System.out.println("----------------------------------------------");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}

}
