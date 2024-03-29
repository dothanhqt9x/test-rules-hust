package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.PostEntity;
import vn.edu.hust.testrules.testruleshust.entity.view.PostDetailView;
import vn.edu.hust.testrules.testruleshust.entity.view.PostSearchView;
import vn.edu.hust.testrules.testruleshust.entity.view.PostViewEntity;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

  String QUERY =
      "SELECT p.id AS post_id,\n"
          + "\t(SELECT u.email\n"
          + "   FROM USER u\n"
          + "   WHERE u.id = p.user_id) AS post_name,\n"
          + "       p.time AS post_time,\n"
          + "       p.content AS content,\n"
          + "       c.id as comment_id,\n"
          + "\t(SELECT u.email\n"
          + "   FROM USER u\n"
          + "   WHERE u.id = c.user_id) as comment_name,\n"
          + "   c.content as comment_content,\n"
          + "   c.time as comment_time,\n"
          + "   (SELECT u.email\n"
          + "   FROM USER u\n"
          + "   WHERE u.id = sc.user_id) as sub_comment_name,\n"
          + "   sc.content as sub_comment_content,\n"
          + "   sc.time as sub_comment_time\n"
          + "FROM post p\n"
          + "INNER JOIN COMMENT c ON p.id = c.post_id\n"
          + "left JOIN sub_comment sc ON c.id = sc.comment_id\n"
          + "WHERE p.id = :postId";

  @Query(nativeQuery = true, value = QUERY)
  List<PostViewEntity> getPostDetails(@Param("postId") Integer id);

  @Query(
      nativeQuery = true,
      value =
          "SELECT (select user.avatar from user where user.id = p.user_id) AS avatarPost, p.id AS postId,\n"
              + "  (SELECT COALESCE(user.name, user.email)\n"
              + "   FROM user\n"
              + "   WHERE user.id = p.user_id) AS postName,\n"
              + "p.content AS postContent, p.time as postTime, c.id as commentId, (SELECT COALESCE(user.name, user.email)\n"
              + "   FROM user\n"
              + "   WHERE user.id = c.user_id) as commentName, c.time as commentTime, c.content as commentContent,\n"
              + "   (SELECT COALESCE(user.name, user.email)\n"
              + "   FROM user\n"
              + "   WHERE user.id = sc.user_id) as subCommentName, sc.time as subCommentTime, sc.content as subCommentContent\n"
              + "FROM post p\n"
              + "LEFT JOIN comment c ON c.post_id = p.id\n"
              + "LEFT JOIN sub_comment sc ON sc.comment_id = c.id\n"
              + "WHERE p.id = :postId")
  List<PostDetailView> getPostJson(@Param("postId") Integer id);

  @Query(
      nativeQuery = true,
      value =
          "SELECT post.user_id AS userId, post.id AS postId, (SELECT user.email FROM user WHERE user.id = post.user_id) AS postName , post.content AS postContent , post.`time` AS postTime, MATCH(content) AGAINST (:key IN BOOLEAN MODE) AS relevance\n"
              + "FROM post\n"
              + "ORDER BY relevance DESC limit 5")
  List<PostSearchView> searchPost(@Param("key") String key);
}
