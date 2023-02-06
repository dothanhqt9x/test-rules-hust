package vn.edu.hust.testrules.testruleshust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.testrules.testruleshust.entity.SchoolEntity;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Integer> {

    SchoolEntity findSchoolEntityById(Integer id);
    List<SchoolEntity> findAll();
}
