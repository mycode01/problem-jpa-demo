package com.example.demo.jpa.persistance;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
  Optional<Product> findByProductId(String productId);

}
