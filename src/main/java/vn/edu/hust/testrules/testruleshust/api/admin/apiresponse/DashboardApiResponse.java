package vn.edu.hust.testrules.testruleshust.api.admin.apiresponse;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DashboardApiResponse {

  private final List<Integer> quantityOfScore;
}
