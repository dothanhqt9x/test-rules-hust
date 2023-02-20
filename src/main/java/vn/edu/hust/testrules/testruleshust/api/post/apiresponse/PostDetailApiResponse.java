package vn.edu.hust.testrules.testruleshust.api.post.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import vn.edu.hust.testrules.testruleshust.api.post.json.CommentJson;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailApiResponse {

  @JsonProperty("post_id")
  private final Integer postId;

  private final String avatar;

  private final String email;
  private final LocalDateTime time;
  private final String content;

  @JsonProperty("comment")
  private final List<CommentJson> comment;
}
