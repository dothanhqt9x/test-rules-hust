package vn.edu.hust.testrules.testruleshust.api.post.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubCommentApiRequest {

  @JsonProperty("content")
  private final String content;

  @JsonProperty("comment_id")
  private final Integer commentId;
}
