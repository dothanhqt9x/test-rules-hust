package vn.edu.hust.testrules.testruleshust.service.question.json;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionJson {

    private String question;
    private List<String> answer;
}
