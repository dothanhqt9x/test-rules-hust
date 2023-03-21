package vn.edu.hust.testrules.testruleshust.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditStatusAccountApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.AccountApiResponse;
import vn.edu.hust.testrules.testruleshust.api.question.apiresponse.UserMaxScoreApiResponse;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterForAppRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.ChangePasswordApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.EditUserApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetRank;
import vn.edu.hust.testrules.testruleshust.entity.SchoolEntity;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.repository.SchoolRepository;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;
import vn.edu.hust.testrules.testruleshust.service.aws.S3BucketStorageService;
import vn.edu.hust.testrules.testruleshust.service.mail.EmailService;
import vn.edu.hust.testrules.testruleshust.service.mail.servicerequest.EmailDetails;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserAService implements UserService {

  private final UserRepository userRepository;
  private final SchoolRepository schoolRepository;
  private final PasswordEncoder passwordEncoder;
  private final S3BucketStorageService service;
  private final EmailService emailService;

  @Value("${application.url.server}")
  private String urlServer;

  @Value("${application.bucket.name}")
  private String bucketName;

  @Override
  public Boolean registerUser(RegisterRequest registerRequest) {

    UserEntity user = userRepository.findUserEntityByEmail(registerRequest.getEmail());

    if (user != null) {
      return false;
    }

    UserEntity userEntity = new UserEntity();

    userEntity.setEmail(registerRequest.getEmail());
    userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    userEntity.setName(registerRequest.getName());
    userEntity.setMssv(registerRequest.getMssv());
    userEntity.setSchool(registerRequest.getSchoolId());
    userEntity.setGender(registerRequest.getGender());

    if (Objects.isNull(registerRequest.getRole())) {
      userEntity.setRole("01");
    } else {
      userEntity.setRole(registerRequest.getRole());
    }
    userEntity.setStatus("1");
    userEntity.setScore(0);
    userRepository.save(userEntity);
    return true;
  }

  @Override
  public GetDetailApiResponse getUserDetail(String email) {
    UserEntity user = userRepository.findUserEntityByEmail(email);

    if (user == null) {
      return null;
    }

    SchoolEntity schoolEntity = schoolRepository.findSchoolEntityById(user.getSchool());

    return GetDetailApiResponse.builder()
        .email(user.getEmail())
        .username(user.getName())
        .mssv(user.getMssv())
        .role(user.getRole())
        .address(user.getAddress())
        .school(schoolEntity.getName())
        .gender(user.getGender())
        .avatar(user.getAvatar())
        .score(user.getScore())
        .build();
  }

  @Override
  public void updateUser(EditUserApiRequest request, String email) {
    UserEntity user = userRepository.findUserEntityByEmail(email);
    user.setName(request.getName());
    user.setAddress(request.getAddress());
    userRepository.save(user);
  }

  @Override
  public void changePassword(ChangePasswordApiRequest request, String email) {
    UserEntity user = userRepository.findUserEntityByEmail(email);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
  }

  @Override
  public void uploadAvatar(MultipartFile file, String email) {

    String fileName = service.uploadFile(file);

    if (Objects.isNull(fileName)) {
      System.out.println("Update file failed");
    }

    UserEntity user = userRepository.findUserEntityByEmail(email);

    user.setAvatar("https://" + bucketName + ".s3.ap-northeast-1.amazonaws.com/" + fileName);

    userRepository.save(user);
  }

  @Override
  public List<AccountApiResponse> getListAccount() {

    List<AccountApiResponse> accountApiResponses = new ArrayList<>();

    List<UserEntity> userEntities = userRepository.getUserEntitiesByRoleOrRole("01", "02");
    userEntities.forEach(
        userEntity -> {
          String role = null;

          if ("01".equals(userEntity.getRole())) {
            role = "Sinh viên";
          }

          if ("02".equals(userEntity.getRole())) {
            role = "Giảng viên";
          }

          accountApiResponses.add(
              AccountApiResponse.builder()
                  .userId(Math.toIntExact(userEntity.getId()))
                  .email(userEntity.getEmail())
                  .role(role)
                  .status(userEntity.getStatus())
                  .build());
        });

    return accountApiResponses;
  }

  @Override
  public void editStatusAccount(EditStatusAccountApiRequest request) throws ServiceException {

    UserEntity user =
        userRepository
            .findById(Long.valueOf(request.getUserId()))
            .orElseThrow(() -> new ServiceException("account_not_found"));

    user.setStatus(request.getStatus());

    userRepository.save(user);
  }

  @Override
  public List<GetRank> getListRankForApp() {
    List<GetRank> rankList = new ArrayList<>();

    List<UserEntity> userEntities = userRepository.getUsersOrderAndLimit();

    userEntities.forEach(
        userEntity ->
            rankList.add(
                GetRank.builder()
                    .username(userEntity.getName())
                    .email(userEntity.getEmail())
                    .avatar(userEntity.getAvatar())
                    .score(userEntity.getScore())
                    .build()));

    return rankList;
  }

  @Override
  public Boolean registerUserForApp(RegisterForAppRequest registerForAppRequest) {

    UserEntity user = userRepository.findUserEntityByEmail(registerForAppRequest.getEmail());

    if (user != null) {
      return false;
    }

    String subject = "Confirm đăng ký trên HUST-EXAM";
    String OTP = generateOTP();
    String linkConfirm = "http://"+urlServer+"/confirmOTPByEmailWhenRegister?email="+ registerForAppRequest.getEmail() +"&OTP="+OTP;
    String button = "<a href=\"" + linkConfirm + "\">"
            + "Confirm mail" + "</a>";
    String body =
            "Chào bạn <br/> LinkConfirm của bạn là: "
                    + button
            + ". <br/> Lưu ý không tiện lộ mail này với bất kỳ ai. <br/> Cám ơn";

    Boolean statusSendEmail = emailService.sendSimpleMail(
            EmailDetails.builder().recipient(registerForAppRequest.getEmail()).subject(subject).msgBody(body).build());

    if (!statusSendEmail) {
      return false;
    }

    UserEntity userEntity = new UserEntity();

    userEntity.setEmail(registerForAppRequest.getEmail());
    userEntity.setPassword(passwordEncoder.encode(registerForAppRequest.getPassword()));
    userEntity.setName(registerForAppRequest.getName());
    userEntity.setSchool(1);
    userEntity.setRole("01");
    userEntity.setStatus("0");
    userEntity.setScore(0);
    userEntity.setOTP(OTP);
    userRepository.save(userEntity);
    return true;
  }

  @Override
  public Boolean forgotPassword(String email) {

    UserEntity userEntity = userRepository.findUserEntityByEmail(email);

    if (Objects.isNull(userEntity)) {
      return Boolean.FALSE;
    }

    if ("0".equals(userEntity.getStatus())) {
      return Boolean.FALSE;
    }

    String subject = "Mã OTP xác nhận quên mật khẩu";
    String OTP = generateOTP();
    String body =
        "Chào bạn \n Mã OTP của bạn là: "
            + OTP
            + ". \n Lưu ý không để ai biết mã OTP của bạn \n Cám ơn";
    Boolean statusSendEmail = emailService.sendSimpleMail(
        EmailDetails.builder().recipient(email).subject(subject).msgBody(body).build());

    if (statusSendEmail) {
      userEntity.setOTP(OTP);
      userEntity.setTimeOTP(LocalDateTime.now());
      userRepository.save(userEntity);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  public Boolean verifyOTP(String email, String otp, String password) {

    UserEntity userEntity = userRepository.findUserEntityByEmail(email);
    long diff = Math.abs(Duration.between(LocalDateTime.now(), userEntity.getTimeOTP()).toSeconds());

    if (otp.equals(userEntity.getOTP()) && diff < 300) {
      userEntity.setPassword(passwordEncoder.encode(password));
      userRepository.save(userEntity);
      return Boolean.TRUE;
    }

    return Boolean.FALSE;
  }

  @Override
  public String confirmOTOByEmailWhenRegister(String email, String otp) throws ServiceException {
    UserEntity userEntity = userRepository.findUserEntityByEmail(email);
    if (Objects.isNull(userEntity)) {
      return "<h1>Tài khoản của bạn không tồn tại</h1>";
    }

    if ("1".equals(userEntity.getStatus())) {
      return "<h1>Tài khoản của bạn đã được active từ trước</h1>";
    }

    if (!otp.equals(userEntity.getOTP())) {
      return "<h1>Xác thực tài khoản không thành công</h1>";
    }

    userEntity.setStatus("1");
    userRepository.save(userEntity);
    return "<h1>Xác thực tài khoản thành công</h1>";
  }

  private String generateOTP() {
    Random random = new Random();
    int randomNumber = random.nextInt(1000000);
    return String.format("%06d", randomNumber);
  }
}
