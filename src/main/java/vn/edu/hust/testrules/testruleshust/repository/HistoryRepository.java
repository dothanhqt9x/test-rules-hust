package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.HistoryEntity;
import vn.edu.hust.testrules.testruleshust.entity.view.HistoryView;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {

  List<HistoryEntity> findHistoryEntitiesByUserId(Integer userId);

  @Query(
      value =
          "select (select COALESCE(user.name, user.email) from user where user.id = h.user_id) as name,h.score as score,h.id as historyId, h.time_test_at as time, u.mssv as mssv from history h inner join `user` u on u.id = h.user_id where u.mssv = :mssv",
      nativeQuery = true)
  List<HistoryView> getListHistoryByMSSV(@Param("mssv") Integer mssv);

  @Query(
      value =
          "select (select COALESCE(user.name, user.email) from user where user.id = h.user_id) as name,h.score as score,h.id as historyId, h.time_test_at as time, u.mssv as mssv from history h inner join `user` u on u.id = h.user_id where h.score between :min and :max ",
      nativeQuery = true)
  List<HistoryView> getListHistoryFilter(@Param("min") Integer min, @Param("max") Integer max);
}
