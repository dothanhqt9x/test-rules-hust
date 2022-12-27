package vn.edu.hust.testrules.testruleshust.service.user;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.ChangePasswordApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.EditUserApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetDetailApiResponse;

@Service
public interface UserService {

    Boolean registerUser(RegisterRequest registerRequest);
    GetDetailApiResponse getUserDetail(String email);
    void updateUser(EditUserApiRequest request, String email);
    void changePassword(ChangePasswordApiRequest request, String email);
}
