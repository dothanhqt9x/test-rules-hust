package vn.edu.hust.testrules.testruleshust.service.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UploadService {

  @Value("${application.upload.folder}")
  private String uploadFolder;

  public Boolean uploadFileToServer(MultipartFile file) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    Path path = Paths.get(uploadFolder + fileName);
    try {
      Files.write(path, file.getBytes());
    } catch (IOException e) {
      return false;
    }
    return true;
  }
}
