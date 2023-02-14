package vn.edu.hust.testrules.testruleshust.api.question.apirequest;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HistoryDetailsApiRequest {

  private final String question;
  private final List<String> answer;
  private final List<Integer> chooses;
  private final Integer flag;
}
