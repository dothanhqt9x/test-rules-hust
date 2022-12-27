package vn.edu.hust.testrules.testruleshust.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.ChangePasswordApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.EditUserApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserAService implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Boolean registerUser(RegisterRequest registerRequest) {

    UserEntity user = userRepository.findUserEntityByEmail(registerRequest.getEmail());

    if (user != null) {
      return false;
    }

    UserEntity userEntity = new UserEntity();

    userEntity.setEmail(registerRequest.getEmail());
    userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    userEntity.setRole("01");
    userRepository.save(userEntity);
    return true;
  }

  @Override
  public GetDetailApiResponse getUserDetail(String email) {
    UserEntity user = userRepository.findUserEntityByEmail(email);

    if (user == null) {
      return null;
    }
    return GetDetailApiResponse.builder()
        .email(user.getEmail())
        .username(user.getName())
        .mssv(user.getMssv())
        .role(user.getRole())
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
}
