package vn.edu.hust.testrules.testruleshust.api.question.get.apiresponse;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionApiResponse {

  private String question;
  private List<String> answer;
  private List<Integer> key;
}
