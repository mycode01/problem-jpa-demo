package com.example.demo.jpa.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.jpa.persistance.Category;
import com.example.demo.jpa.persistance.ProductRepo;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DefaultServiceTest {

  @Autowired
  ProductRepo repo;

  @BeforeEach
  void init() {
  }

  @Test
  void testGetProduct() {
    DefaultService service = new DefaultService(repo);
    service.product("testId1");
  }
}