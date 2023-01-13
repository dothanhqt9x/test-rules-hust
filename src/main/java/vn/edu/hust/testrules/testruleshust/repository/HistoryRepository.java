package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.HistoryEntity;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {

  List<HistoryEntity> findHistoryEntitiesByUserId(Integer userId);
}
