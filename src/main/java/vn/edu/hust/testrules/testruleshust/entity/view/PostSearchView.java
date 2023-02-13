package vn.edu.hust.testrules.testruleshust.entity.view;

import java.time.LocalDateTime;

public interface PostSearchView {

    Integer getPostId();

    String getPostName();

    String getPostContent();

    LocalDateTime getPostTime();

    Integer getUserId();
}
