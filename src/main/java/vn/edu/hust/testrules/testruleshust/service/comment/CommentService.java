package vn.edu.hust.testrules.testruleshust.service.comment;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.CommentApiRequest;

@Service
public interface CommentService {

    void createComment(CommentApiRequest request, String email);
}
