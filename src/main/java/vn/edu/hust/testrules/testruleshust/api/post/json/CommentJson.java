package vn.edu.hust.testrules.testruleshust.api.post.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentJson {

  @JsonProperty("comment_id")
  private final Integer commentId;

  private final String email;
  private final LocalDateTime time;
  private final String content;

  @JsonProperty("sub_comment")
  private final List<SubCommentJson> subComment;
}
