package vn.edu.hust.testrules.testruleshust.api.admin.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountApiResponse {

    private final Integer userId;
    private final String email;
    private final String role;
    private final String status;
}
