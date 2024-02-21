package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.bookstore.model"})
@EnableJpaRepositories(basePackages = {"com.bookstore.repository"})
@ComponentScan(basePackages = {"com.bookstore"})
public class BookStoreApplication {

	public static void main(String[] args) {

		System.out.println("BookStore application");
		SpringApplication.run(BookStoreApplication.class, args);
	}

}
