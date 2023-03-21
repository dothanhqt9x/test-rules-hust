package vn.edu.hust.testrules.testruleshust.api.security.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.LoginRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterForAppRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse.LoginResponse;
import vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse.RegisterResponse;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.exception.response.ErrorResponse;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;
import vn.edu.hust.testrules.testruleshust.security.jwt.CustomUserDetails;
import vn.edu.hust.testrules.testruleshust.security.jwt.JwtTokenProvider;
import vn.edu.hust.testrules.testruleshust.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final JwtTokenProvider tokenProvider;

  private final UserService userService;

  @PostMapping("/login")
  public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws ServiceException {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

    UserEntity userEntity = userRepository.findUserEntityByEmail(authentication.getName());

    if ("0".equals(userEntity.getStatus())) {
      throw new ServiceException("Tài khoản chưa xác thực");
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
    return new LoginResponse(jwt);
  }

  @PostMapping("/register")
  public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

    if (Boolean.FALSE.equals(validateRequest(registerRequest))) {
      return ResponseEntity.badRequest()
          .body(ErrorResponse.builder().result("ng").errorMessage("Invalid gender").build());
    }

    if (Boolean.TRUE.equals(userService.registerUser(registerRequest))) {
      return ResponseEntity.ok().body(RegisterResponse.builder().status("OK").build());
    }

    return ResponseEntity.ok(RegisterResponse.builder().status("NG").build());
  }

  @PostMapping("/registerForApp")
  public ResponseEntity<Object> registerUserForApp(@Valid @RequestBody RegisterForAppRequest registerForAppRequest) throws ServiceException {

    if (Boolean.TRUE.equals(userService.registerUserForApp(registerForAppRequest))) {
      return ResponseEntity.ok().body(RegisterResponse.builder().status("OK").build());
    }

    throw new ServiceException("Tài khoản đã tồn tài hoặc chưa xác thực");

//    return ResponseEntity.ok(RegisterResponse.builder().status("NG").build());
  }

  @GetMapping("/confirmOTPByEmailWhenRegister")
  public String confirmOTOByEmailWhenRegister(@RequestParam String email, @RequestParam String OTP) throws ServiceException {

    return userService.confirmOTOByEmailWhenRegister(email, OTP);
  }

  private Boolean validateRequest(RegisterRequest registerRequest) {

    return "Nam".equals(registerRequest.getGender()) || "Nữ".equals(registerRequest.getGender());
  }
}
