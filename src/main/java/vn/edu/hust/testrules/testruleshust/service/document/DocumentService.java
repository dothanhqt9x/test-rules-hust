package vn.edu.hust.testrules.testruleshust.service.document;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.entity.DocumentEntity;
import vn.edu.hust.testrules.testruleshust.service.document.servicerequest.DocumentServiceRequest;

import java.util.List;

@Service
public interface DocumentService {

    List<DocumentEntity> getListDocument();
    void createDocument(DocumentServiceRequest request);
    void editDocument(DocumentServiceRequest request, Integer documentId);
}
