package com.example.demo;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        productRepository.save(new Product((Long) null, "computer",  1250.0,   4));
        productRepository.save(new Product((Long) null,  "Book", 150.0, 40));
        productRepository.save(new Product((Long) null,   "Pen", 1.5,  100));
        productRepository.save(new Product((Long) null,   "mouse", 75.5, 25));
    }

}
