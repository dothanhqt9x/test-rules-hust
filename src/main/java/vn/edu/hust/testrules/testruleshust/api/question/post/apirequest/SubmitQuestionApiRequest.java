package vn.edu.hust.testrules.testruleshust.api.question.post.apirequest;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SubmitQuestionApiRequest {

  private final Integer questionNumber;
  private final List<Integer> chooses;
  private final Integer flag;
}
