package vn.edu.hust.testrules.testruleshust.service.mail.servicerequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailDetails {

  private final String recipient;
  private final String msgBody;
  private final String subject;
  private final String attachment;
}
