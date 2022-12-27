package vn.edu.hust.testrules.testruleshust.api.question.post.apirequest;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionPostApiRequest {

  private String question;
  private List<String> answer;
  private List<Integer> key;
}
