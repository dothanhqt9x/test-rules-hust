package vn.edu.hust.testrules.testruleshust.api.admin.apirequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditStatusAccountApiRequest {

    private final Integer userId;
    private final String status;
}
