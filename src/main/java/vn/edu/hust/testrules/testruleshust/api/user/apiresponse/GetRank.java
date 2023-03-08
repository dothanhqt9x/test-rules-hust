package vn.edu.hust.testrules.testruleshust.api.user.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetRank {

  private final String username;
  private final String email;
  private final String avatar;
  private final Integer score;
}
