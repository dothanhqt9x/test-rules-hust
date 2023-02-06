package vn.edu.hust.testrules.testruleshust.api.admin.apirequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditSchoolApiRequest {

    private final Integer id;
    private final String schoolName;
}
