package vn.edu.hust.testrules.testruleshust.api.user.apiresponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetDetailApiResponse {

    private final String username;
    private final String email;
    private final String role;
    private final String mssv;
    private final String school;
    private final String address;
    private final String gender;
    private final String avatar;
    private final Integer score;
}
