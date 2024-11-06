package com.ncsu.ebooks;

import com.ncsu.ebooks.database.DBConnector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EbooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbooksApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(DBConnector dbConnector) {
		return args -> {
			dbConnector.connectToDB();
		};
	}
}
