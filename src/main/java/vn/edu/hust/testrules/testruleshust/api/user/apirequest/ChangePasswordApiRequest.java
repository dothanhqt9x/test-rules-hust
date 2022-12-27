package vn.edu.hust.testrules.testruleshust.api.user.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordApiRequest {

    @JsonProperty("password")
    private String password;
}
