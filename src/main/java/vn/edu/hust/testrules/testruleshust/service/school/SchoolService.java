package vn.edu.hust.testrules.testruleshust.service.school;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.CreateSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.SchoolApiResponse;

import java.util.List;

@Service
public interface SchoolService {

  void createSchool(CreateSchoolApiRequest request);

  List<SchoolApiResponse> getListSchool();

  void editSchool(EditSchoolApiRequest request);
}
