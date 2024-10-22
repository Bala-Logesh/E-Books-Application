package com.ncsu.ebooks;

import com.ncsu.ebooks.database.DBConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbooksApplication.class, args);
		DBConnector.connectToDB();
	}

}
