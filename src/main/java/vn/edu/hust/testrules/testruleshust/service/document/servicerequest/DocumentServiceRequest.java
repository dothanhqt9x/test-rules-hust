package vn.edu.hust.testrules.testruleshust.service.document.servicerequest;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class DocumentServiceRequest {

  private final String name;
  private final MultipartFile file;
}
