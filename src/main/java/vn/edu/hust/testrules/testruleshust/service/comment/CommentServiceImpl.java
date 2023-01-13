package vn.edu.hust.testrules.testruleshust.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.CommentApiRequest;
import vn.edu.hust.testrules.testruleshust.entity.CommentEntity;
import vn.edu.hust.testrules.testruleshust.repository.CommentRepository;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  @Override
  public void createComment(CommentApiRequest request, String email) {

    // userId
    Integer userId = Math.toIntExact(userRepository.findUserEntityByEmail(email).getId());

    CommentEntity commentEntity = new CommentEntity();
    commentEntity.setContent(request.getContent());
    commentEntity.setTime(LocalDateTime.now());
    commentEntity.setUserId(userId);
    commentEntity.setPostId(request.getPostId());

    commentRepository.save(commentEntity);
  }
}
