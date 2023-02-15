package vn.edu.hust.testrules.testruleshust.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "USER")
@Entity
@Data
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String password;
  private String mssv;
  private String email;
  private String role;
  private String address;
  private Integer school;
  private String gender;
  private String avatar;
  private Integer score;
}
