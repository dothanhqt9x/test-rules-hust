package vn.edu.hust.testrules.testruleshust.api.post.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentApiRequest {

    @JsonProperty("post_id")
    private final Integer postId;

    @JsonProperty("content")
    private final String content;
}
