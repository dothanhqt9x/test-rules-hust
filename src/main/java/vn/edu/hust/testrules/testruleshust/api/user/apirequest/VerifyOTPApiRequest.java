package vn.edu.hust.testrules.testruleshust.api.user.apirequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyOTPApiRequest {

    private final String email;
    private final String OTP;
    private final String password;
}
