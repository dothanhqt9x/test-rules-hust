package vn.edu.hust.testrules.testruleshust.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.CreateSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.SchoolApiResponse;
import vn.edu.hust.testrules.testruleshust.service.school.SchoolService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

  private final SchoolService schoolService;

  @PostMapping("/createSchool")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createSchool(@RequestBody CreateSchoolApiRequest request) {

    schoolService.createSchool(request);
  }

  @GetMapping("/getListSchool")
  public List<SchoolApiResponse> getListSchool() {

    return schoolService.getListSchool();
  }

  @PostMapping("/editSchool")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void editNameSchool(@RequestBody EditSchoolApiRequest request) {
    schoolService.editSchool(request);
  }
}
