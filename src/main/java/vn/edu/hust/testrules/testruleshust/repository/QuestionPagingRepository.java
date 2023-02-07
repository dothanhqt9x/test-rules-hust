package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.QuestionEntity;

@Repository
public interface QuestionPagingRepository
    extends PagingAndSortingRepository<QuestionEntity, Integer> {}
