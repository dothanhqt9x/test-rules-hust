package vn.edu.hust.testrules.testruleshust.api.admin.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllQuestionApiResponse {

    private final Integer questionNumber;
    private final String question;
}
