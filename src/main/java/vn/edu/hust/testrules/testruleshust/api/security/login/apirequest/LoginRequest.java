package vn.edu.hust.testrules.testruleshust.api.security.login.apirequest;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class LoginRequest {

  @NonNull
  private String email;

  @NonNull
  private String password;
}
