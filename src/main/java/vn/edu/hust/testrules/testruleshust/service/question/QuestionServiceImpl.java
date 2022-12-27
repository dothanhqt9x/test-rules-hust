package vn.edu.hust.testrules.testruleshust.service.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.question.getall.apiresponse.QuestionGetAllApiResponse;
import vn.edu.hust.testrules.testruleshust.entity.QuestionEntity;
import vn.edu.hust.testrules.testruleshust.repository.QuestionCRUDRepository;
import vn.edu.hust.testrules.testruleshust.repository.QuestionRepository;
import vn.edu.hust.testrules.testruleshust.service.question.json.QuestionJson;
import vn.edu.hust.testrules.testruleshust.service.question.servicerequest.QuestionServiceRequest;
import vn.edu.hust.testrules.testruleshust.service.question.serviceresponse.QuestionServiceResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final ObjectMapper objectMapper;

  private final QuestionRepository questionRepository;
  private final QuestionCRUDRepository questionCRUDRepository;

  String FILE_NAME =
      "E:\\20221\\test-rules-hust\\src\\main\\java\\vn\\edu\\hust\\testrules\\testruleshust\\lcs\\list_cau_hoi_1.docx";

  @Override
  public QuestionServiceResponse getOneQuestion() throws JsonProcessingException {
    Optional<QuestionEntity> entityOptional = questionRepository.findById(1);
    QuestionEntity entity = entityOptional.get();

    String questionJson = entity.getQuestionJson();
    String answer = entity.getAnswer();
    String[] strings = answer.replace("[", "").replace("]", "").split(",");
    Integer[] numbers = new Integer[strings.length];
    for (int i = 0; i < numbers.length; i++) {
      try {
        numbers[i] = Integer.parseInt(strings[i]);
      } catch (NumberFormatException nfe) {
        numbers[i] = null;
      }
    }
    QuestionJson question = objectMapper.readValue(questionJson, QuestionJson.class);
    return QuestionServiceResponse.builder()
        .question(question.getQuestion())
        .answer(question.getAnswer())
        .key(Arrays.stream(numbers).toList())
        .build();
  }

  @Override
  public void insertNewQuestion(QuestionServiceRequest request) throws JsonProcessingException {
    String questionJson =
        objectMapper.writeValueAsString(
            QuestionJson.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build());
    QuestionEntity entity = new QuestionEntity();
    entity.setType("01");
    entity.setQuestionJson(questionJson);
    entity.setAnswer(request.getKey().toString());
    questionRepository.save(entity);
  }

  @Override
  public List<QuestionGetAllApiResponse> getAllQuestion() {
    List<QuestionGetAllApiResponse> questionGetAllApiResponseList = new ArrayList<>();
    List<QuestionEntity> questionEntities = questionRepository.findAll();
    for (int k = 0; k < 20; k++) {
      QuestionEntity questionEntity = questionEntities.get(k);
      try {
        QuestionJson question =
                objectMapper.readValue(questionEntity.getQuestionJson(), QuestionJson.class);
        String[] strings =
                questionEntity
                        .getAnswer()
                        .replace("[", "")
                        .replace("]", "")
                        .replaceAll("\\s+", "")
                        .split(",");
        Integer[] numbers = new Integer[strings.length];
        for (int i = 0; i < numbers.length; i++) {
          try {
            numbers[i] = Integer.parseInt(strings[i]);
          } catch (NumberFormatException nfe) {
            numbers[i] = null;
          }
        }
        questionGetAllApiResponseList.add(
                QuestionGetAllApiResponse.builder()
                        .question(question.getQuestion())
                        .answer(question.getAnswer())
                        .key(Arrays.stream(numbers).toList())
                        .build());
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
//    questionEntities.forEach(
//        questionEntity -> {
//          try {
//            QuestionJson question =
//                objectMapper.readValue(questionEntity.getQuestionJson(), QuestionJson.class);
//            String[] strings =
//                questionEntity
//                    .getAnswer()
//                    .replace("[", "")
//                    .replace("]", "")
//                    .replaceAll("\\s+", "")
//                    .split(",");
//            Integer[] numbers = new Integer[strings.length];
//            for (int i = 0; i < numbers.length; i++) {
//              try {
//                numbers[i] = Integer.parseInt(strings[i]);
//              } catch (NumberFormatException nfe) {
//                numbers[i] = null;
//              }
//            }
//            questionGetAllApiResponseList.add(
//                QuestionGetAllApiResponse.builder()
//                    .question(question.getQuestion())
//                    .answer(question.getAnswer())
//                    .key(Arrays.stream(numbers).toList())
//                    .build());
//          } catch (JsonProcessingException e) {
//            e.printStackTrace();
//          }
//        });

    return questionGetAllApiResponseList;
  }

  @Override
  public void insertAllQuestion() throws IOException {
    try (XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(FILE_NAME)))) {
      XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
      String docText = xwpfWordExtractor.getText();

      String[] blocks = docText.split("\n\n");
      for (int i = 0; i < blocks.length; i++) {
        String[] block = blocks[i].split("\n");
        String question = block[0].substring(3); // question
        String[] stringKeys = block[block.length - 1].replace(" ", "").substring(7).split(",");
        ArrayList<Integer> keys = new ArrayList<>(); // keys
        ArrayList<String> answer = new ArrayList<>();
        Arrays.stream(stringKeys)
            .toList()
            .forEach(
                key -> {
                  switch (key) {
                    case "A":
                      keys.add(1);
                      break;
                    case "B":
                      keys.add(2);
                      break;
                    case "C":
                      keys.add(3);
                      break;
                    case "D":
                      keys.add(4);
                      break;
                    default:
                      System.out.println("Key not correct");
                  }
                });
        for (int j = 1; j < block.length - 1; j++) {
            answer.add(block[j].substring(3));
        }
        String questionJson =
                objectMapper.writeValueAsString(
                        QuestionJson.builder()
                                .question(question)
                                .answer(answer)
                                .build());
        QuestionEntity entity = new QuestionEntity();
        entity.setId(i+1);
        entity.setQuestionJson(questionJson);
        entity.setAnswer(keys.toString());
        entity.setType("01");
        questionRepository.save(entity);
      }
    }
  }
}
