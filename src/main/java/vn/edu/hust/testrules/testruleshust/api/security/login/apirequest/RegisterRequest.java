package vn.edu.hust.testrules.testruleshust.api.security.login.apirequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequest {

  private final String email;
  private final String password;
  private final String mssv;
  private final Integer schoolId;
  private final String name;
}
