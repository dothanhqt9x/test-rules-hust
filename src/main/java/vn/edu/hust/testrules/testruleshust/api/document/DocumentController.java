package vn.edu.hust.testrules.testruleshust.api.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.entity.DocumentEntity;
import vn.edu.hust.testrules.testruleshust.service.document.DocumentService;
import vn.edu.hust.testrules.testruleshust.service.document.servicerequest.DocumentServiceRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

  @GetMapping("/getListDocument")
  public List<DocumentEntity> getListDocument() {

    return documentService.getListDocument();
  }

  @PostMapping("/createDocument")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createDocument(
      @RequestParam("name") String name,
      @RequestParam(name = "file", required = false) MultipartFile file) {

    documentService.createDocument(DocumentServiceRequest.builder().name(name).file(file).build());
  }

  @PostMapping("/editDocument/{documentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void editDocument(
      @RequestParam("name") String name,
      @RequestParam(name = "file", required = false) MultipartFile file,
      @PathVariable Integer documentId) {

    documentService.editDocument(
        DocumentServiceRequest.builder().name(name).file(file).build(), documentId);
  }

  @PostMapping("/deleteDocument/{documentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<String> deleteDocument(@PathVariable Integer documentId) {

    Boolean isDelete = documentService.deleteDocumentById(documentId);
    if (isDelete) {
      return ResponseEntity.ok().body("OK");
    }

    return ResponseEntity.badRequest().body("NG");
  }
}
