package vn.edu.hust.testrules.testruleshust.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.PostApiRequest;
import vn.edu.hust.testrules.testruleshust.api.post.apiresponse.PostDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.api.post.json.CommentJson;
import vn.edu.hust.testrules.testruleshust.api.post.json.SubCommentJson;
import vn.edu.hust.testrules.testruleshust.entity.PostEntity;
import vn.edu.hust.testrules.testruleshust.entity.view.PostDetailView;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.repository.PostPagingRepository;
import vn.edu.hust.testrules.testruleshust.repository.PostRepository;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostPagingRepository postPagingRepository;
  private final UserRepository userRepository;

  @Override
  public void createPost(PostApiRequest request, String email) {

    // userId
    Integer userId = Math.toIntExact(userRepository.findUserEntityByEmail(email).getId());

    PostEntity postEntity = new PostEntity();
    postEntity.setContent(request.getContent());
    postEntity.setTime(LocalDateTime.now());
    postEntity.setUserId(userId);

    postRepository.save(postEntity);
  }

  @Override
  public List<PostEntity> findPaginated(int pageNo, int pageSize) {
    Pageable paging = PageRequest.of(pageNo, pageSize);
    Page<PostEntity> pagedResult = postPagingRepository.findAll(paging);
    return pagedResult.toList();
  }

  @Override
  public PostDetailApiResponse getPostDetail(Integer postId) throws ServiceException {

    List<PostDetailView> postDetailViews = postRepository.getPostJson(postId);

    // post
    LocalDateTime postTime = postDetailViews.get(0).getPostTime();
    String postName = postDetailViews.get(0).getPostName();
    String postContent = postDetailViews.get(0).getPostContent();

    // comment
    List<CommentJson> commentJsons = new ArrayList<>();

    Set<Integer> commentIds = new HashSet<>();
    for (int i = 0; i < postDetailViews.size(); i++) {
      commentIds.add(postDetailViews.get(i).getCommentId());
    }

    for (Integer i : commentIds) {

      // build sub_comment
      List<SubCommentJson> subCommentJsons = new ArrayList<>();

      for (int j = 0; j < postDetailViews.size(); j++) {
        PostDetailView postDetailView = postDetailViews.get(j);
        if (postDetailView.getCommentId() == i) {
          subCommentJsons.add(
              SubCommentJson.builder()
                  .email(postDetailView.getSubCommentName())
                  .time(postDetailView.getSubCommentTime())
                  .content(postDetailView.getSubCommentContent())
                  .build());
        }
      }
      if (subCommentJsons.get(0).getEmail() == null) {
        commentJsons.add(
            CommentJson.builder()
                .commentId(i)
                .email(postDetailViews.get(i).getCommentName())
                .content(postDetailViews.get(i).getCommentContent())
                .time(postDetailViews.get(i).getCommentTime())
                .subComment(null)
                .build());
      } else {
        commentJsons.add(
            CommentJson.builder()
                .commentId(i)
                .email(postDetailViews.get(i).getCommentName())
                .content(postDetailViews.get(i).getCommentContent())
                .time(postDetailViews.get(i).getCommentTime())
                .subComment(subCommentJsons)
                .build());
      }
    }

    return PostDetailApiResponse.builder()
        .postId(postId)
        .email(postName)
        .time(postTime)
        .content(postContent)
        .comment(commentJsons)
        .build();
  }
}
