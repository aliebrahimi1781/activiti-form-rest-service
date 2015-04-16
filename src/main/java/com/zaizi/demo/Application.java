package com.zaizi.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring boot application
 * @author kvaratharaja
 *
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

	/**
	 * Main function to start spring boot
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
