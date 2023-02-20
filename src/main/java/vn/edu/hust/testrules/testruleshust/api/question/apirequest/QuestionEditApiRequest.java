package vn.edu.hust.testrules.testruleshust.api.question.apirequest;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionEditApiRequest {

  private String question;
  private List<String> answer;
  private List<Integer> key;
}
