package vn.edu.hust.testrules.testruleshust.api.security.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.LoginRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apirequest.RegisterRequest;
import vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse.LoginResponse;
import vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse.RandomStuff;
import vn.edu.hust.testrules.testruleshust.api.security.login.apiresponse.RegisterResponse;
import vn.edu.hust.testrules.testruleshust.security.jwt.CustomUserDetails;
import vn.edu.hust.testrules.testruleshust.security.jwt.JwtTokenProvider;
import vn.edu.hust.testrules.testruleshust.service.user.UserService;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider tokenProvider;

  private final UserService userService;

  @PostMapping("/login")
  public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
    return new LoginResponse(jwt);
  }

  // Api /api/random yêu cầu phải xác thực mới có thể request
  @GetMapping("/random")
  public RandomStuff randomStuff() {
    return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
  }

  @PostMapping("/register")
  public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {

    if (userService.registerUser(registerRequest) == true) {
      return RegisterResponse.builder().status("OK").build();
    }

    return RegisterResponse.builder().status("NG").build();
  }
}
