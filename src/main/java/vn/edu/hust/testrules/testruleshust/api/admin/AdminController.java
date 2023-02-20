package vn.edu.hust.testrules.testruleshust.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.CreateSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.AllQuestionApiResponse;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.DashboardApiResponse;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.HistoryForGetListApiResponse;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.SchoolApiResponse;
import vn.edu.hust.testrules.testruleshust.service.question.QuestionService;
import vn.edu.hust.testrules.testruleshust.service.school.SchoolService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

  private final SchoolService schoolService;
  private final QuestionService questionService;

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

  @GetMapping("/getListQuestion/{pageNo}/{pageSize}")
  public List<AllQuestionApiResponse> getListQuestion(
      @PathVariable Integer pageNo, @PathVariable Integer pageSize) {

    return questionService.getListQuestion(pageNo, pageSize);
  }

  @GetMapping("/getListHistorySearch")
  public List<HistoryForGetListApiResponse> getListHistoryByEmail(
      @RequestParam("search") Integer mssv) {

    return questionService.getListHistoryByMSSV(mssv);
  }

  @GetMapping("/getListHistoryFilter")
  public List<HistoryForGetListApiResponse> getListHistoryFilter(
      @RequestParam("min") Integer min, @RequestParam("max") Integer max) {

    return questionService.getListHistoryFilter(min, max);
  }

  @GetMapping("/getDashboard")
  public DashboardApiResponse getDashboard() {

    return questionService.getDashboard();
  }
}
