package vn.edu.hust.testrules.testruleshust.api.admin.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HistoryForGetListApiResponse {

  private final Integer id;
  private final String time;
  private final Integer mssv;
  private final String name;
  private final Integer score;
}
