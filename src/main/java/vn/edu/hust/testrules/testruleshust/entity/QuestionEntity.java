package vn.edu.hust.testrules.testruleshust.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "QUESTION")
@Entity
@Data
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    @Column(name = "question_json")
    private String questionJson;

    private String answer;
    private String image;

    @Column(name = "question_number")
    private Integer questionNumber;
}
