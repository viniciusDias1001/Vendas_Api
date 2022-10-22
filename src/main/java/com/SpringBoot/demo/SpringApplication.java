package com.SpringBoot.demo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringApplication extends SpringBootServletInitializer {


	public static void main(String[] args ) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);


	}

}
