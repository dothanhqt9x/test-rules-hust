package vn.edu.hust.testrules.testruleshust.api.post.apiresponse;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostApiResponse {

  private final Integer id;

  private final Integer userId;

  private final String content;

  private final LocalDateTime time;

  private final String username;
}
