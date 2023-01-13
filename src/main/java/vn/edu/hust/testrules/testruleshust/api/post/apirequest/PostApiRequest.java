package vn.edu.hust.testrules.testruleshust.api.post.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostApiRequest {

  @JsonProperty("content")
  private String content;
}
