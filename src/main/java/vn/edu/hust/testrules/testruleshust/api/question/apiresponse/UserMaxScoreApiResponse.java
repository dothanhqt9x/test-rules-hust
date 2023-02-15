package vn.edu.hust.testrules.testruleshust.api.question.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMaxScoreApiResponse {
    
    private final String email;
    private final String username;
    private final String avatar;
    private final Integer score;
}
