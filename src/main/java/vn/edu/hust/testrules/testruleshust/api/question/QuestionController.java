package vn.edu.hust.testrules.testruleshust.api.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.question.apirequest.QuestionEditApiRequest;
import vn.edu.hust.testrules.testruleshust.api.question.apirequest.SubmitQuestionApiRequest;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.HistoryApiResponse;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.QuestionApiResponse;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.QuestionGetAllApiResponse;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.UserMaxScoreApiResponse;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.service.question.QuestionService;
import vn.edu.hust.testrules.testruleshust.service.question.json.HistoryJson;
import vn.edu.hust.testrules.testruleshust.service.question.servicerequest.QuestionServiceRequest;
import vn.edu.hust.testrules.testruleshust.service.question.serviceresponse.QuestionServiceResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;
  private final ObjectMapper objectMapper;

  @PostMapping("/question/create")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createQuestion(
      @RequestParam("question") String requestFromApi,
      @RequestParam(name = "file", required = false) MultipartFile file)
      throws JsonProcessingException, ServiceException {
    QuestionEditApiRequest request =
        objectMapper.readValue(requestFromApi, QuestionEditApiRequest.class);
    QuestionServiceRequest questionServiceRequest =
        QuestionServiceRequest.builder()
            .question(request.getQuestion())
            .answer(request.getAnswer())
            .key(request.getKey())
            .image(file)
            .build();
    questionService.insertNewQuestion(questionServiceRequest);
  }

  @PostMapping("/question/createAll")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createAllQuestion() {
    try {
      questionService.insertAllQuestion();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @PostMapping("/question/submit")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void submitQuestion(@RequestBody List<SubmitQuestionApiRequest> requests) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      questionService.submitQuestion(requests, authentication.getName());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @GetMapping("/get_history_list")
  public List<HistoryApiResponse> getListHistory() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return questionService.getListHistory(authentication.getName());
  }

  @GetMapping("/get_history_details")
  public List<HistoryJson> getHistoryDetails(@RequestParam(name = "id") Integer id)
      throws JsonProcessingException, JSONException, ServiceException {

    return questionService.getHistoryDetails(id);
  }

  @GetMapping("/question")
  public QuestionApiResponse getOnlyQuestion() throws JsonProcessingException {
    QuestionServiceResponse questionServiceResponse = questionService.getOneQuestion();
    return QuestionApiResponse.builder()
        .question(questionServiceResponse.getQuestion())
        .answer(questionServiceResponse.getAnswer())
        .key(questionServiceResponse.getKey())
        .build();
  }

  @GetMapping("/question/all")
  List<QuestionGetAllApiResponse> getAllQuestion(@RequestParam("size") Integer size) {
    return questionService.getAllQuestion(size);
  }

  @PostMapping("/question/submitForApp")
  public List<UserMaxScoreApiResponse> submitQuestionForApp(@RequestParam("score") Integer score) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return questionService.submitQuestionForApp(authentication.getName(), score);
  }

  @PostMapping("/question/edit")
  public void editQuestion(@RequestBody QuestionEditApiRequest request, @RequestParam("questionNumber") Integer questionNumber) throws JsonProcessingException, ServiceException {

    questionService.editQuestion(request, questionNumber);
  }

  @GetMapping("/getQuestionForApp")
  List<QuestionGetAllApiResponse> getQuestionForApp() {
    return questionService.getQuestionForApp();
  }
}
