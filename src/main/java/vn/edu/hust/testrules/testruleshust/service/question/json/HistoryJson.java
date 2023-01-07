package vn.edu.hust.testrules.testruleshust.service.question.json;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HistoryJson {

  private final String question;
  private final List<String> answer;
  private final List<Integer> chooses;
  private final Integer flag;
}
