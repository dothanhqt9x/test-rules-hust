package vn.edu.hust.testrules.testruleshust.api.security.login.apirequest;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class RegisterRequest {

  @Email
  @Pattern(
      regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
      message = "Invalid email address")
  private final String email;

  private final String password;

  @Pattern(regexp="[\\d]{6}")

  private final String mssv;
  private final Integer schoolId;
  private final String name;
  private final String gender;
}
