package vn.edu.hust.testrules.testruleshust.api.security.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.LoginRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse.LoginResponse;
import vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse.RegisterResponse;
import vn.edu.hust.testrules.testruleshust.exception.response.ErrorResponse;
import vn.edu.hust.testrules.testruleshust.security.jwt.CustomUserDetails;
import vn.edu.hust.testrules.testruleshust.security.jwt.JwtTokenProvider;
import vn.edu.hust.testrules.testruleshust.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider tokenProvider;

  private final UserService userService;

  @PostMapping("/login")
  public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

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

  private Boolean validateRequest(RegisterRequest registerRequest) {

    return "Nam".equals(registerRequest.getGender()) || "Nữ".equals(registerRequest.getGender());
  }
}
