package vn.edu.hust.testrules.testruleshust.api.question.post.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HistoryApiResponse {

  private final Integer id; // id of table history
  private final String name; // time of table history
}
