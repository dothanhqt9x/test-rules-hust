package vn.edu.hust.testrules.testruleshust.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.edu.hust.testrules.testruleshust.security.jwt.JwtAuthenticationFilter;
import vn.edu.hust.testrules.testruleshust.service.user.UserServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired UserServiceImpl userService;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // Get AuthenticationManager Bean
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService) // Cung cáp userservice cho spring security
        .passwordEncoder(passwordEncoder()); // cung cấp password encoder
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/login",
            "/register",
            "/getListSchool",
            "/registerForApp",
            "/forgotPassword",
            "/verifyOTP",
            "/confirmOTPByEmailWhenRegister")
        .permitAll() // Cho phép tất cả mọi người truy cập vào 2 địa chỉ này
        .antMatchers(
            "/user/edit",
            "/change/password",
            "/get_history_list",
            "/question/all",
            "/question/submit",
            "/create/post",
            "/getAllPost",
            "/addComment",
            "/addSubComment",
            "/getPostDetail",
            "/searchPost",
            "/uploadAvatar",
            "/submitForApp")
        .hasAnyAuthority("01", "02")
        .antMatchers(
            "/question/create",
            "/getListQuestion/**",
            "/createDocument",
            "/editDocument",
            "/getListHistoryFilter",
            "/getListHistorySearch",
            "/deleteDocument/**")
        .hasAnyAuthority("02", "03")
        .antMatchers("/getListDocument", "/get_history_details", "/user/detail")
        .hasAnyAuthority("01", "02", "03")
        .antMatchers("/createSchool", "/editSchool", "/getListAccount", "/editStatusAccount")
        .hasAuthority("03"); // Tất cả các request khác đều cần phải xác thực mới được truy cập

    // Thêm một lớp Filter kiểm tra jwt
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
