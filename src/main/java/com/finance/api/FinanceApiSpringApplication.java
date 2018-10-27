package com.finance.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.finance.api.config.property.FinanceApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(FinanceApiProperty.class)
public class FinanceApiSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApiSpringApplication.class, args);
	}
}
