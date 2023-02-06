package vn.edu.hust.testrules.testruleshust.api.admin.apirequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolApiRequest {

    private String schoolName;
}
