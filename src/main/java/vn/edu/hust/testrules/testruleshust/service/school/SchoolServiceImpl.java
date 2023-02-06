package vn.edu.hust.testrules.testruleshust.service.school;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.CreateSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.SchoolApiResponse;
import vn.edu.hust.testrules.testruleshust.entity.SchoolEntity;
import vn.edu.hust.testrules.testruleshust.repository.SchoolRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

  private final SchoolRepository schoolRepository;

  @Override
  public void createSchool(CreateSchoolApiRequest request) {

    SchoolEntity schoolEntity = new SchoolEntity();
    schoolEntity.setName(request.getSchoolName());

    schoolRepository.save(schoolEntity);
  }

  @Override
  public List<SchoolApiResponse> getListSchool() {

    List<SchoolEntity> schoolEntities = schoolRepository.findAll();

    List<SchoolApiResponse> schoolApiResponses = new ArrayList<>();

    schoolEntities.forEach(
        schoolEntity ->
            schoolApiResponses.add(
                SchoolApiResponse.builder()
                    .id(schoolEntity.getId())
                    .name(schoolEntity.getName())
                    .build()));

    return schoolApiResponses;
  }

  @Override
  public void editSchool(EditSchoolApiRequest request) {

    SchoolEntity schoolEntity = schoolRepository.findSchoolEntityById(request.getId());
    schoolEntity.setName(request.getSchoolName());
    schoolRepository.save(schoolEntity);
  }
}
