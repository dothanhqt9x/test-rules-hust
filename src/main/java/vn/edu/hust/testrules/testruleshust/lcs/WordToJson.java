package vn.edu.hust.testrules.testruleshust.lcs;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import vn.edu.hust.testrules.testruleshust.service.question.servicerequest.QuestionServiceRequest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class WordToJson {

  public static void main(String[] args) throws Exception {

    String FILE_NAME =
        "E:\\20221\\test-rules-hust\\src\\main\\java\\vn\\edu\\hust\\testrules\\testruleshust\\lcs\\list_cau_hoi_1.docx";

    try (XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(FILE_NAME)))) {
      XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
      String docText = xwpfWordExtractor.getText();
      System.out.println(docText);

      QuestionServiceRequest questionServiceRequest = null;

      String[] questionOnly = xwpfWordExtractor.getText().split("\n\n");
      String question1 = questionOnly[0];
      String[] lines = question1.split("\n");
      Question question = new Question();
      ArrayList<String> answer = new ArrayList<>();
      ArrayList<Integer> key = new ArrayList<>();
      for (int i = 1; i < lines.length - 1; i++) {
        answer.add(lines[i]);
      }
      question.setQuestion(lines[0]);
      question.setAnswer(answer);

      System.out.println("question" + question.getQuestion() + question.getAnswer());

      //      String[] questionAll = docText.split("\\n");
      //      Arrays.stream(questionAll)
      //          .toList()
      //          .forEach(
      //              question -> {
      //
      //              });

      // find number of words in the document
      long count = Arrays.stream(docText.split("\\s+")).count();
      System.out.println("Total words: " + count);
    }
  }
}
