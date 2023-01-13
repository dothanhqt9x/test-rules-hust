package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.SubCommentEntity;

@Repository
public interface SubCommentRepository extends JpaRepository<SubCommentEntity, Integer> {}
