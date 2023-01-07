package vn.edu.hust.testrules.testruleshust.api.question.post.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HistoryApiResponse {

  private final Integer id;
  private final String name;
}
