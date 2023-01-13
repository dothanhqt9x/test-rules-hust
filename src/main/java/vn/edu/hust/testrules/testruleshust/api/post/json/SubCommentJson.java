package vn.edu.hust.testrules.testruleshust.api.post.json;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SubCommentJson {

  private final String email;
  private final LocalDateTime time;
  private final String content;
}
