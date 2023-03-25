package vn.edu.hust.testrules.testruleshust.api.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.service.upload.UploadService;

@RestController
@RequiredArgsConstructor
public class UploadFileController {

    private final UploadService uploadService;

    @PostMapping("/uploadFileToServer")
    public ResponseEntity<String> uploadFileToServer(@RequestParam(name = "file", required = false) MultipartFile file) throws ServiceException {

        if (uploadService.uploadFileToServer(file)) {
            return ResponseEntity.ok().body("Upload File Success");
        }

        return ResponseEntity.badRequest().body("Upload File Failed");
    }
}
