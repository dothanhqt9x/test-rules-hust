package vn.edu.hust.testrules.testruleshust.api.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.CommentApiRequest;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.PostApiRequest;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.SubCommentApiRequest;
import vn.edu.hust.testrules.testruleshust.api.post.apiresponse.PostApiResponse;
import vn.edu.hust.testrules.testruleshust.api.post.apiresponse.PostDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.service.comment.CommentService;
import vn.edu.hust.testrules.testruleshust.service.post.PostService;
import vn.edu.hust.testrules.testruleshust.service.subcomment.SubCommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;
  private final CommentService commentService;
  private final SubCommentService subCommentService;

  @PostMapping("/create/post")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createPost(@RequestBody PostApiRequest request) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    postService.createPost(request, authentication.getName());
  }

  @GetMapping("/getAllPost/{pageNo}/{pageSize}")
  public List<PostApiResponse> getAllPost(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {

    return postService.findPaginated(pageNo, pageSize);
  }

  @PostMapping("/addComment")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addComment(@RequestBody CommentApiRequest request) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    commentService.createComment(request, authentication.getName());
  }

  @PostMapping("/addSubComment")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addSubComment(@RequestBody SubCommentApiRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    subCommentService.createSubComment(request, authentication.getName());
  }

  @GetMapping("/getPostDetail/{postId}")
  public PostDetailApiResponse getPostDetails(@PathVariable Integer postId)
      throws ServiceException {
    return postService.getPostDetail(postId);
  }

  @GetMapping("/searchPost")
  public List<PostApiResponse> searchPost(@RequestParam("key") String key) {
    return postService.searchPost(key);
  }
}
