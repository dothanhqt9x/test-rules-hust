package vn.edu.hust.testrules.testruleshust.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.entity.DocumentEntity;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;
import vn.edu.hust.testrules.testruleshust.repository.DocumentRepository;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;
import vn.edu.hust.testrules.testruleshust.service.aws.S3BucketStorageService;
import vn.edu.hust.testrules.testruleshust.service.document.servicerequest.DocumentServiceRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

  private final DocumentRepository documentRepository;
  private final UserRepository userRepository;
  private final S3BucketStorageService service;

  @Override
  public List<DocumentEntity> getListDocument() {

    List<DocumentEntity> documentEntities = documentRepository.findAll();

    return documentEntities;
  }

  @Override
  public void createDocument(DocumentServiceRequest request) {
    String fileName = service.uploadFile(request.getFile());

    if (Objects.isNull(fileName)) {
      System.out.println("Update file failed");
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserEntity user = userRepository.findUserEntityByEmail(authentication.getName());

    DocumentEntity documentEntity = new DocumentEntity();
    documentEntity.setName(request.getName());
    documentEntity.setLink("https://test-rules-hust.s3.ap-northeast-1.amazonaws.com/" + fileName);
    documentEntity.setCreateAt(LocalDateTime.now());
    documentEntity.setCreateBy(Math.toIntExact(user.getId()));
    documentRepository.save(documentEntity);
  }

  @Override
  public void editDocument(DocumentServiceRequest request, Integer documentId) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserEntity user = userRepository.findUserEntityByEmail(authentication.getName());

    DocumentEntity documentEntity = documentRepository.findDocumentEntityById(documentId);

    if (!request.getFile().isEmpty()) {
      String fileName = service.uploadFile(request.getFile());
      documentEntity.setLink(fileName);
    }

    documentEntity.setName(request.getName());
    documentEntity.setCreateAt(LocalDateTime.now());
    documentEntity.setCreateBy(Math.toIntExact(user.getId()));

    documentRepository.save(documentEntity);
  }
}
