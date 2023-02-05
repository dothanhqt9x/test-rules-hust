package vn.edu.hust.testrules.testruleshust.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "SCHOOL")
@Entity
@Data
public class SchoolEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;
}
