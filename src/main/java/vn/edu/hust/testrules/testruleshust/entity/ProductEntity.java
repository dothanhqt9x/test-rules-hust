package vn.edu.hust.testrules.testruleshust.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "PRODUCT")
@Entity
@Data
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String logo;

  private Integer price;

  private String description;

  private Integer countClick;

  private Integer categoryId;
}
