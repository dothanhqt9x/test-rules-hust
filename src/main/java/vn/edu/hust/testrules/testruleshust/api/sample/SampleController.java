package vn.edu.hust.testrules.testruleshust.api.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.aws.S3Util;
import vn.edu.hust.testrules.testruleshust.entity.ProductEntity;
import vn.edu.hust.testrules.testruleshust.service.ProductService;
import vn.edu.hust.testrules.testruleshust.service.aws.S3BucketStorageService;

@RestController
@RequiredArgsConstructor
public class SampleController {

  private final S3BucketStorageService service;
  private final ProductService productService;

  @GetMapping("/api/sample")
  public ResponseEntity<ProductEntity> getProductById() {
    return ResponseEntity.ok().body(productService.getProductById(1));
  }

  @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void upload(@RequestParam("file") MultipartFile multipart) {

    service.uploadFile(multipart);
  }
}
