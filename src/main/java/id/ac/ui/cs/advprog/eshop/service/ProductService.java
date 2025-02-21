package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductService {
    Product create(Product product);
    List<Product> findAll();
    Product findById(String id);
    void update(Product product);
    void delete(Product product);
}