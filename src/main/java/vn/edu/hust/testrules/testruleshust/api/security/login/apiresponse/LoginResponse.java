package vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginResponse {

  private String accessToken;
  private String tokenType = "Bearer";

  public LoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
