package com.example.demo.jpa.service;

import com.example.demo.jpa.persistance.Product;
import com.example.demo.jpa.persistance.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultService {

  private final ProductRepo repo;

  public DefaultService(ProductRepo repo) {
    this.repo = repo;
  }

  public void product(String productId) {
    Product p = repo.findByProductId(productId).orElseThrow(RuntimeException::new);
    log.debug(p.detail().title());
  }

}
