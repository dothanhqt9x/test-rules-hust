package vn.edu.hust.testrules.testruleshust.service.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class S3BucketStorageService {

  private Logger logger = LoggerFactory.getLogger(S3BucketStorageService.class);

  @Autowired private AmazonS3 amazonS3Client;

  @Value("${application.bucket.name}")
  private String bucketName;

  /**
   * Upload file into AWS S3
   *
   * @param file MultipartFile
   * @return String
   */
  public String uploadFile(MultipartFile file) {
    String keyName = file.getOriginalFilename() + LocalDateTime.now();
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      amazonS3Client.putObject(
          new PutObjectRequest(bucketName, keyName, file.getInputStream(), metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead));
      return keyName;
    } catch (IOException ioe) {
      logger.error("IOException: " + ioe.getMessage());
    } catch (AmazonServiceException serviceException) {
      logger.info("AmazonServiceException: " + serviceException.getMessage());
      throw serviceException;
    } catch (AmazonClientException clientException) {
      logger.info("AmazonClientException Message: " + clientException.getMessage());
      throw clientException;
    }
    return null;
  }
}
