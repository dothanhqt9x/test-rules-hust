package vn.edu.hust.testrules.testruleshust.service.subcomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.post.apirequest.SubCommentApiRequest;
import vn.edu.hust.testrules.testruleshust.entity.SubCommentEntity;
import vn.edu.hust.testrules.testruleshust.repository.SubCommentRepository;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubCommentServiceImpl implements SubCommentService {

  private final UserRepository userRepository;
  private final SubCommentRepository subCommentRepository;

  @Override
  public void createSubComment(SubCommentApiRequest request, String email) {

    // userId
    Integer userId = Math.toIntExact(userRepository.findUserEntityByEmail(email).getId());

    SubCommentEntity subCommentEntity = new SubCommentEntity();
    subCommentEntity.setTime(LocalDateTime.now());
    subCommentEntity.setCommentId(request.getCommentId());
    subCommentEntity.setContent(request.getContent());
    subCommentEntity.setUserId(userId);

    subCommentRepository.save(subCommentEntity);
  }
}
