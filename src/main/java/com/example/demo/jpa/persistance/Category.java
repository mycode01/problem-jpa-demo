package com.example.demo.jpa.persistance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  private String catValue;

  // some more


  protected Category(){}
  public Category(String catValue) {
    this.catValue = catValue;
  }
}
