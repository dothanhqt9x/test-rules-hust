package vn.edu.hust.testrules.testruleshust.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "HISTORY")
@Entity
@Data
public class HistoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "history_json")
  private String historyJson;

  private Integer score;

  @Column(name = "time_test_at")
  private LocalDateTime timeTestAt;

  @Column(name = "question_list")
  private String questionList;
}
