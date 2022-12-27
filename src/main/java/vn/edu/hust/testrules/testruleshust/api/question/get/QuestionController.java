package vn.edu.hust.testrules.testruleshust.api.question.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.testrules.testruleshust.api.question.get.apiresponse.QuestionApiResponse;
import vn.edu.hust.testrules.testruleshust.service.question.QuestionService;
import vn.edu.hust.testrules.testruleshust.service.question.serviceresponse.QuestionServiceResponse;

@RestController
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping("/question")
  public QuestionApiResponse getOnlyQuestion() throws JsonProcessingException {
    QuestionServiceResponse questionServiceResponse = questionService.getOneQuestion();
    return QuestionApiResponse.builder()
        .question(questionServiceResponse.getQuestion())
        .answer(questionServiceResponse.getAnswer())
        .key(questionServiceResponse.getKey())
        .build();
  }
}
