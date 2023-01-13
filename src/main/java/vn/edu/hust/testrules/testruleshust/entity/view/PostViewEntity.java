package vn.edu.hust.testrules.testruleshust.entity.view;

import lombok.Data;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Table(name = "POST_VIEW")
public class PostViewEntity {

  private Integer post_id;
//  private String post_name;
//  private LocalDateTime post_time;
//  private String content;
//
//  private Integer comment_id;
//  private String comment_name;
//  private String comment_content;
//  private LocalDateTime comment_time;
//
//  private String sub_comment_name;
//  private String sub_comment_content;
//  private LocalDateTime sub_comment_time;
}
