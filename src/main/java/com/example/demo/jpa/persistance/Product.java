package com.example.demo.jpa.persistance;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @Column(name = "product_id", length = 16, unique = true, nullable = false)
  private String productId;

  @ManyToOne
  @JoinColumn(name = "user_no", insertable = false, updatable = false)
  private User owner;

  @OneToOne(mappedBy = "product")
  private Detail detail; // here!

  @ManyToMany
  @JoinTable(name = "mapper_prod_cat", joinColumns = @JoinColumn(name = "product_no"),
      inverseJoinColumns = @JoinColumn(name = "category_no"))
  private List<Category> categories;

  @OneToMany
  @JoinColumn(name = "product_id", referencedColumnName = "product_id")
  private List<Tag> tags;

  protected Product(){}

  public Product(String productId, User owner, Detail detail,
      List<Category> categories, List<Tag> tags) {
    this.productId = productId;
    this.owner = owner;
    this.detail = detail;
    this.categories = categories;
    this.tags = tags;
  }

  public Detail detail() {
    return detail;
  }
}


