package vn.edu.hust.testrules.testruleshust.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

  UserEntity user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    if ("01".equals(user.getRole()) && "1".equals(user.getStatus())) {
      return Collections.singleton(new SimpleGrantedAuthority("01"));
    }

    if ("02".equals(user.getRole()) && "1".equals(user.getStatus())) {
      return Collections.singleton(new SimpleGrantedAuthority("02"));
    }

    if ("03".equals(user.getRole())) {
      return Collections.singleton(new SimpleGrantedAuthority("03"));
    }

    return null;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
