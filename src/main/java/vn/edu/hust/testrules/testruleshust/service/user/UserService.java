package vn.edu.hust.testrules.testruleshust.service.user;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditStatusAccountApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.AccountApiResponse;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterForAppRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.ChangePasswordApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.EditUserApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetRank;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;

import java.util.List;

@Service
public interface UserService {

    Boolean registerUser(RegisterRequest registerRequest);
    GetDetailApiResponse getUserDetail(String email);
    void updateUser(EditUserApiRequest request, String email);
    void changePassword(ChangePasswordApiRequest request, String email);
    void uploadAvatar(MultipartFile file, String email);

    List<AccountApiResponse> getListAccount();

    void editStatusAccount(EditStatusAccountApiRequest request) throws ServiceException;

    List<GetRank> getListRankForApp();

    Boolean registerUserForApp(RegisterForAppRequest registerForAppRequest);

    Boolean forgotPassword(String email);

    Boolean verifyOTP(String email, String otp, String password);
}
