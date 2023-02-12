package vn.edu.hust.testrules.testruleshust.api.admin.apiresponse;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AllQuestionApiResponse {

    private final Integer questionNumber;
    private final String question;
    private final List<String> answer;
    private final List<Integer> key;
}
