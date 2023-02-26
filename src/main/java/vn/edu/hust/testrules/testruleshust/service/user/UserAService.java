package vn.edu.hust.testrules.testruleshust.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.admin.apirequest.EditStatusAccountApiRequest;
import vn.edu.hust.testrules.testruleshust.api.admin.apiresponse.AccountApiResponse;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.ChangePasswordApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.EditUserApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.entity.SchoolEntity;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.repository.SchoolRepository;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;
import vn.edu.hust.testrules.testruleshust.service.aws.S3BucketStorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserAService implements UserService {

  private final UserRepository userRepository;
  private final SchoolRepository schoolRepository;
  private final PasswordEncoder passwordEncoder;
  private final S3BucketStorageService service;

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
    userEntity.setRole("01");
    userEntity.setStatus("1");
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

    user.setAvatar("https://"+bucketName+".s3.ap-northeast-1.amazonaws.com/" + fileName);

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
}
