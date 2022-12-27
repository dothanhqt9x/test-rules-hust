package vn.edu.hust.testrules.testruleshust.api.question.getall;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.testrules.testruleshust.api.question.getall.apiresponse.QuestionGetAllApiResponse;
import vn.edu.hust.testrules.testruleshust.service.question.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionGetAllController {

  private final QuestionService questionService;

  @GetMapping("/question/all")
  List<QuestionGetAllApiResponse> getAllQuestion() {
    return questionService.getAllQuestion();
  }
}
