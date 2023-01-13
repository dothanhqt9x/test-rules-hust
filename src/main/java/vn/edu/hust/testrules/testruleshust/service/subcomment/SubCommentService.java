package vn.edu.hust.testrules.testruleshust.service.subcomment;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.SubCommentApiRequest;

@Service
public interface SubCommentService {

    void createSubComment(SubCommentApiRequest request, String email);
}
