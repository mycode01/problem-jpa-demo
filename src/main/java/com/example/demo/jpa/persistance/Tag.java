package com.example.demo.jpa.persistance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  private String tagValue;

  // some more


  protected Tag() {
  }

  public Tag(String tagValue) {
    this.tagValue = tagValue;
  }
}
