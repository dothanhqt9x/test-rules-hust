package vn.edu.hust.testrules.testruleshust.api.admin.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchoolApiResponse {

  private final Integer id;
  private final String name;
}
