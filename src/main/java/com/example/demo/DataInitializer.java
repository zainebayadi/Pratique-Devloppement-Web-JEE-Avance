package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Ajout de données initiales si la base est vide
        if (productRepository.count() == 0) {
            productRepository.save(new Product(null, "Laptop", 999.99, 10));
            productRepository.save(new Product(null, "Mouse", 29.99, 50));
            productRepository.save(new Product(null, "Keyboard", 79.99, 30));
            System.out.println("✅ Données initiales insérées dans la base.");
        } else {
            System.out.println("ℹ️ Données déjà présentes, aucune insertion.");
        }
    }
}
