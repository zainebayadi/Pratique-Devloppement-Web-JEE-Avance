package com.example.demo.services;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ServiceProductImp implements ServiceProduct{
    private final ProductRepository  productRepository;
    private static final int LOW_STOCK_THRESHOLD = 10;

    public ServiceProductImp(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        if(id == null || id <= 0){
            throw new IllegalArgumentException("id de produit est invalide");
        }
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> findLowStockProducts() {
        return productRepository.findByQuantityIsLessThanEqual(LOW_STOCK_THRESHOLD);
    }

    @Override
    public Product addStock(Long productId, Integer quantity) {
        if(quantity == null || quantity <= 0){
            throw new IllegalArgumentException("quantity ajouter doit etre positive");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product non trouver avec id " + productId));
        int newQuantity = product.getQuantity() + quantity;
        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }

    @Override
    public Product removeStock(Long productId, Integer quantity) {
        if(quantity == null || quantity <= 0){
            throw new IllegalArgumentException("quantity ajouter doit etre positive");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product non trouver avec id " + productId));

        if(product.getQuantity() < quantity){
            throw new RuntimeException(
                    "stock insuffisant.disponible:" + product.getQuantity() + "demander"+quantity
            );
        }

        int newQuantity = product.getQuantity() - quantity;
        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }
}
