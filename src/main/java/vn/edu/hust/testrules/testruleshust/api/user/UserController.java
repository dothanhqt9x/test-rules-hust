package vn.edu.hust.testrules.testruleshust.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.ChangePasswordApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apirequest.EditUserApiRequest;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetDetailApiResponse;
import vn.edu.hust.testrules.testruleshust.api.user.apiresponse.GetRank;
import vn.edu.hust.testrules.testruleshust.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/user/detail")
  public GetDetailApiResponse getUserDetail(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userService.getUserDetail(authentication.getName());
  }

  @PostMapping("/user/edit")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateUser(@RequestBody EditUserApiRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userService.updateUser(request, authentication.getName());
  }

  @PostMapping("/change/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changePassword(@RequestBody @Valid ChangePasswordApiRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userService.changePassword(request, authentication.getName());
  }

  @PostMapping("/uploadAvatar")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateAvatar(@RequestParam(name = "file", required = false) MultipartFile file) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userService.uploadAvatar(file, authentication.getName());
  }

  @GetMapping("/getListRank")
  public List<GetRank> getListRankForApp() {
    return userService.getListRankForApp();
  }
}
