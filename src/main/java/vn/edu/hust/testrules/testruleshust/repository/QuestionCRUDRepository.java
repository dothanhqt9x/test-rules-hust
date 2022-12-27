package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.QuestionEntity;

@Repository
public interface QuestionCRUDRepository extends CrudRepository<QuestionEntity, Integer> {
}
