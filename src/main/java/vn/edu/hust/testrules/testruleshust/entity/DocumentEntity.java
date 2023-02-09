package vn.edu.hust.testrules.testruleshust.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "DOCUMENT")
@Entity
@Data
public class DocumentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String link;

  @Column(name = "create_by")
  private Integer createBy;

  @Column(name = "create_at")
  private LocalDateTime createAt;
}
