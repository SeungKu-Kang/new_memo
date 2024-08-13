package com.memo.new_memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewMemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewMemoApplication.class, args);
	}

}
