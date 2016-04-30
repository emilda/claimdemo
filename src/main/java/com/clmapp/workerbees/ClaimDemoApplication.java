package com.clmapp.workerbees;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClaimDemoApplication {

	public static String ROOT = "upload";
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(ClaimDemoApplication.class, args);

	}
	
	@RequestMapping("/myhome")
	public String home() {
		return "Hello Worker Bees Docker";
	}
	
	@Bean
    CommandLineRunner init() {
        return (String[] args) -> {
            new File(ROOT).mkdir();
            log.debug("Created dir to upload files into");
        };
    }
}
