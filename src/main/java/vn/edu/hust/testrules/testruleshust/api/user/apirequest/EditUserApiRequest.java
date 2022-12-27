package vn.edu.hust.testrules.testruleshust.api.user.apirequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditUserApiRequest {

  private final String name;
  private final String address;
}
