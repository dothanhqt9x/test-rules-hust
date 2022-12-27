package vn.edu.hust.testrules.testruleshust.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.entity.UserEntity;
import vn.edu.hust.testrules.testruleshust.repository.UserRepository;
import vn.edu.hust.testrules.testruleshust.security.jwt.CustomUserDetails;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService{

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity user = userRepository.findUserEntityByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new CustomUserDetails(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    UserEntity user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

    return new CustomUserDetails(user);
  }


}
