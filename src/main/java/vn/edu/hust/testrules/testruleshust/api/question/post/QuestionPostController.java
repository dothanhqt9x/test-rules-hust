package vn.edu.hust.testrules.testruleshust.api.question.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.testrules.testruleshust.api.question.post.apirequest.QuestionPostApiRequest;
import vn.edu.hust.testrules.testruleshust.service.question.QuestionService;
import vn.edu.hust.testrules.testruleshust.service.question.servicerequest.QuestionServiceRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class QuestionPostController {

  private final QuestionService questionService;

  @PostMapping("/question/create")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createQuestion(@RequestBody QuestionPostApiRequest request)
      throws JsonProcessingException {
    QuestionServiceRequest questionServiceRequest =
        QuestionServiceRequest.builder()
            .question(request.getQuestion())
            .answer(request.getAnswer())
            .key(request.getKey())
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
}
