package vn.edu.hust.testrules.testruleshust.entity.view;

import java.time.LocalDateTime;

public interface PostDetailView {

  Integer getPostId();

  String getAvatarPost();

  String getPostName();

  String getPostContent();

  LocalDateTime getPostTime();

  Integer getCommentId();

  String getCommentName();

  LocalDateTime getCommentTime();

  String getCommentContent();

  String getSubCommentName();

  LocalDateTime getSubCommentTime();

  String getSubCommentContent();
}
