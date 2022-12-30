package vn.edu.hust.testrules.testruleshust.api.question.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.question.post.apirequest.QuestionPostApiRequest;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.service.aws.S3BucketStorageService;
import vn.edu.hust.testrules.testruleshust.service.question.QuestionService;
import vn.edu.hust.testrules.testruleshust.service.question.servicerequest.QuestionServiceRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class QuestionPostController {

  private final QuestionService questionService;
  private final ObjectMapper objectMapper;

  @PostMapping("/question/create")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createQuestion(
      @RequestParam("question") String requestFromApi, @RequestParam("file") MultipartFile file)
      throws JsonProcessingException, ServiceException {
    QuestionPostApiRequest request =
        objectMapper.readValue(requestFromApi, QuestionPostApiRequest.class);
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
}
