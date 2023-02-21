package vn.edu.hust.testrules.testruleshust.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.CreateSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditSchoolApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditStatusAccountApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.*;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.service.question.QuestionService;
import vn.edu.hust.testrules.testruleshust.service.school.SchoolService;
import vn.edu.hust.testrules.testruleshust.service.user.UserAService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AdminController {

  private final SchoolService schoolService;
  private final QuestionService questionService;
  private final UserAService userAService;

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

  @GetMapping("/getListAccount")
  public List<AccountApiResponse> getListAccount() {

    return userAService.getListAccount();
  }

  @PostMapping("/editStatusAccount")
  public ResponseEntity<String> editStatusAccount(@RequestBody EditStatusAccountApiRequest request) throws ServiceException {

    if ("0".equals(request.getStatus()) || "1".equals(request.getStatus())) {
      userAService.editStatusAccount(request);

      return ResponseEntity.ok().body("OK");
    }

    return ResponseEntity.badRequest().body("NG");
  }
}
