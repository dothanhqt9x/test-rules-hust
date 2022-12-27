package vn.edu.hust.testrules.testruleshust.service.question.serviceresponse;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionServiceResponse {

  private String question;
  private List<String> answer;
  private List<Integer> key;
}
