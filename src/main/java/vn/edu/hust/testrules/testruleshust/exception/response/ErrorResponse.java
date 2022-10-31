package vn.edu.hust.testrules.testruleshust.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

  @JsonProperty("result")
  private final String result;

  @JsonProperty("errorMessage")
  private final List<String> errorMessage;
}
