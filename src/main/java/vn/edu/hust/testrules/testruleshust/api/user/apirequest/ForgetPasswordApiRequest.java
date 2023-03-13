package vn.edu.hust.testrules.testruleshust.api.user.apirequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordApiRequest {

    private String email;
}
