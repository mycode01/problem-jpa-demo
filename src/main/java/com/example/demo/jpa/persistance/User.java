package com.example.demo.jpa.persistance;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  private String name;

//  @OneToMany(fetch = FetchType.LAZY)
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_no")
  private List<Product> products;

  protected User(){}

  public User(String name) {
    this.name = name;
  }
}
