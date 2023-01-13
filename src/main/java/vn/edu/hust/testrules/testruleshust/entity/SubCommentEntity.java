package vn.edu.hust.testrules.testruleshust.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "SUB_COMMENT")
@Entity
@Data
public class SubCommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "content")
  private String content;

  @Column(name = "time")
  private LocalDateTime time;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "comment_id")
  private Integer commentId;
}
