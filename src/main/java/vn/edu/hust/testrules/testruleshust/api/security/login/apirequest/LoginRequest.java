package vn.edu.hust.testrules.testruleshust.api.security.login.apirequest;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class LoginRequest {

  @Email
  @Pattern(
      regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
      message = "Invalid email address")
  private String email;

  private String password;
}
