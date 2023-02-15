package vn.edu.hust.testrules.testruleshust.api.question.apiresponse;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionGetAllApiResponse {

  private Integer questionNumber;
  private String question;
  private List<String> answer;
  private List<Integer> key;
}
