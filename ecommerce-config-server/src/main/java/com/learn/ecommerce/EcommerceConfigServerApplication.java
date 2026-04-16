package com.learn.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class EcommerceConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceConfigServerApplication.class, args);
	}
//  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git
}
