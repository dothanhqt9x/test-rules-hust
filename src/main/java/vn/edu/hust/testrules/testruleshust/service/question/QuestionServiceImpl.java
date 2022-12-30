package vn.edu.hust.testrules.testruleshust.service.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.question.getall.apiresponse.QuestionGetAllApiResponse;
import vn.edu.hust.testrules.testruleshust.entity.QuestionEntity;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.repository.QuestionCRUDRepository;
import vn.edu.hust.testrules.testruleshust.repository.QuestionRepository;
import vn.edu.hust.testrules.testruleshust.service.aws.S3BucketStorageService;
import vn.edu.hust.testrules.testruleshust.service.question.json.QuestionJson;
import vn.edu.hust.testrules.testruleshust.service.question.servicerequest.QuestionServiceRequest;
import vn.edu.hust.testrules.testruleshust.service.question.serviceresponse.QuestionServiceResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final ObjectMapper objectMapper;

  private final QuestionRepository questionRepository;
  private final QuestionCRUDRepository questionCRUDRepository;
  private final S3BucketStorageService service;

  String FILE_NAME =
      "E:\\sourcecode\\test-rules-hust\\src\\main\\java\\vn\\edu\\hust\\testrules\\testruleshust\\lcs\\list_cau_hoi_1.docx";

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
  public void insertNewQuestion(QuestionServiceRequest request)
      throws JsonProcessingException, ServiceException {

    System.out.println("Start time" + System.currentTimeMillis());

    String questionRequest = convertToTextStandard(request.getQuestion(), request.getAnswer());
    List<QuestionEntity> questionEntities = questionRepository.findAll();

    if (Objects.isNull(request.getImage())) {
      checkText(questionEntities, questionRequest);
    } else {
      checkTextAndImage(questionEntities, questionRequest, request);
    }

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
    return questionGetAllApiResponseList;
  }

  @Override
  public void insertAllQuestion() throws IOException {
    try (XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(FILE_NAME)))) {
      XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
      String docText =
          xwpfWordExtractor
              .getText()
              .replace("A.", "")
              .replace("B.", "")
              .replace("C.", "")
              .replace("D.", "");

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
          answer.add(block[j]);
        }
        String questionJson =
            objectMapper.writeValueAsString(
                QuestionJson.builder().question(question).answer(answer).build());
        QuestionEntity entity = new QuestionEntity();
        entity.setQuestionNumber(i + 1);
        entity.setQuestionJson(questionJson);
        entity.setAnswer(keys.toString());
        entity.setType("01");
        questionRepository.save(entity);
      }
    }
  }

  private int LCS(char[] X, char[] Y) {
    int m = X.length;
    int n = Y.length;
    int LCStuff[][] = new int[m + 1][n + 1];

    int result = 0;

    for (int i = 0; i <= m; i++) {
      for (int j = 0; j <= n; j++) {
        if (i == 0 || j == 0) LCStuff[i][j] = 0;
        else if (X[i - 1] == Y[j - 1]) {
          LCStuff[i][j] = LCStuff[i - 1][j - 1] + 1;
          result = Integer.max(result, LCStuff[i][j]);
        } else LCStuff[i][j] = 0;
      }
    }
    return result;
  }

  private String convertToTextStandard(String question, List<String> answers) {
    String regex =
        "[^a-z0-9A-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]";
    String result = question.toLowerCase(Locale.ROOT).replaceAll(regex, "");
    for (int i = 0; i < answers.size(); i++) {
      result = result.concat(answers.get(i).toLowerCase(Locale.ROOT).replaceAll(regex, ""));
    }
    return result;
  }

  private void checkText(List<QuestionEntity> questionEntities, String questionRequest)
      throws ServiceException, JsonProcessingException {
    int length = questionEntities.size();
    for (int i = 0; i < length; i++) {
      QuestionEntity questionEntity = questionEntities.get(i);
      QuestionJson questionJson =
          objectMapper.readValue(questionEntity.getQuestionJson(), QuestionJson.class);
      String question = questionJson.getQuestion();
      List<String> answers = questionJson.getAnswer();
      String questionTextStandard = convertToTextStandard(question, answers);
      int lcs = LCS(questionRequest.toCharArray(), questionTextStandard.toCharArray());
      if (lcs / Math.min(questionRequest.length(), questionTextStandard.length()) >= 0.8) {
        throw new ServiceException("question_duplicate");
      }
    }
  }

  private void checkTextAndImage(
      List<QuestionEntity> questionEntities, String questionRequest, QuestionServiceRequest request)
      throws JsonProcessingException, ServiceException {
    int length = questionEntities.size();
    for (int i = 0; i < length; i++) {
      QuestionEntity questionEntity = questionEntities.get(i);
      QuestionJson questionJson =
          objectMapper.readValue(questionEntity.getQuestionJson(), QuestionJson.class);
      String question = questionJson.getQuestion();
      List<String> answers = questionJson.getAnswer();
      String questionTextStandard = convertToTextStandard(question, answers);
      int lcs = LCS(questionRequest.toCharArray(), questionTextStandard.toCharArray());
      double lcsForImage;
      double lcsForText = lcs / Math.min(questionRequest.length(), questionTextStandard.length());
      if (Objects.nonNull(questionEntity.getImage())) {
        lcsForImage = CheckForImage(request.getImage(), questionEntity.getImage());
        if (lcsForImage <= 20 && lcsForText >= 0.6) {
          throw new ServiceException("question_duplicate");
        }
      } else if (lcsForText >= 0.8) {
        throw new ServiceException("question_duplicate");
      } else {
        service.uploadFile(request.getImage());
      }
    }
  }

  private double CheckForImage(MultipartFile file, String image) {

    BufferedImage imgA = null;
    BufferedImage imgB = null;

    try {

      // convert MultipartFile to File
      File fileA = new File(Objects.requireNonNull(file.getOriginalFilename()));
      FileOutputStream fos = new FileOutputStream(fileA);
      fos.write(file.getBytes());
      fos.close();
      URL fileB = new URL(image);

      // Reading files
      imgA = ImageIO.read(fileA);
      imgB = ImageIO.read(fileB);
    } catch (IOException e) {
      System.out.println(e);
    }

    int width1 = imgA.getWidth();
    int height1 = imgA.getHeight();

    imgB = resizeImage(imgB, width1, height1);

    long difference = 0;

    for (int y = 0; y < height1; y++) {

      for (int x = 0; x < width1; x++) {

        int rgbA = imgA.getRGB(x, y);
        int rgbB = imgB.getRGB(x, y);
        int redA = (rgbA >> 16) & 0xff;
        int greenA = (rgbA >> 8) & 0xff;
        int blueA = (rgbA) & 0xff;
        int redB = (rgbB >> 16) & 0xff;
        int greenB = (rgbB >> 8) & 0xff;
        int blueB = (rgbB) & 0xff;

        difference += Math.abs(redA - redB);
        difference += Math.abs(greenA - greenB);
        difference += Math.abs(blueA - blueB);
      }
    }

    double total_pixels = width1 * height1 * 3;

    double avg_different_pixels = difference / total_pixels;

    return (avg_different_pixels / 255) * 100;
  }

  private BufferedImage resizeImage(
      BufferedImage originalImage, int targetWidth, int targetHeight) {
    BufferedImage resizedImage =
        new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = resizedImage.createGraphics();
    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    graphics2D.dispose();
    return resizedImage;
  }
}
