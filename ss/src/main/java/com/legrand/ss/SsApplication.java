package com.legrand.ss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SsApplication.class, args);
	}
}
