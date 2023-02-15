package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findUserEntityByEmail(String email);

  @Query(value = "select * from `user` u order by u.score desc limit 5", nativeQuery = true)
  List<UserEntity> getUsersOrderAndLimit();
}
