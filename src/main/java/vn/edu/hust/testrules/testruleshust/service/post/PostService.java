package vn.edu.hust.testrules.testruleshust.service.post;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.PostApiRequest;
import vn.edu.hust.testrules.testruleshust.api.post.apiresponse.PostApiResponse;
import vn.edu.hust.testrules.testruleshust.api.post.apiresponse.PostDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;

import java.util.List;

@Service
public interface PostService {

    void createPost(PostApiRequest request, String email);
    List<PostApiResponse> findPaginated(int pageNo, int pageSize);
    PostDetailApiResponse getPostDetail(Integer postId) throws ServiceException;
}
