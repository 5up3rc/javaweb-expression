package org.javaweb.exp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.javaweb.*"})
public class JavaWebExpApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaWebExpApplication.class, args);
	}

}
