package com.example.demo.jpa.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detail")
public class Detail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @OneToOne
  @JoinColumn(name = "product_id", referencedColumnName = "product_id")
  private Product product;

  @Column(name = "prod_title")
  private String title;

  @Column(name = "prod_desc")
  private String desc;

  protected Detail(){}

  public Detail(String title, String desc) {
    this.title = title;
    this.desc = desc;
  }

  public String title() {
    return title;
  }

  public String desc() {
    return desc;
  }
}
