package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.QuestionEntity;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

  QuestionEntity findByQuestionNumber(Integer questionNumber);

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM question WHERE JSON_LENGTH(question_json->'$.answer') = 4 and JSON_LENGTH(answer -> '$') = 1")
  List<QuestionEntity> findQuestionForApp();
}
