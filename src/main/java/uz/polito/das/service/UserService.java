package uz.polito.das.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
 import uz.polito.das.dto.UserDTO;
import uz.polito.das.model.CustomUser;
import uz.polito.das.model.Role;
import uz.polito.das.model.User;
import uz.polito.das.repository.RoleRepository;
import uz.polito.das.repository.UserRepository;

import java.util.*;


@Service(value = "userService")
public class UserService implements UserDetailsService {
  private final Log logger = LogFactory.getLog(UserService.class);
  @Autowired
  private BCryptPasswordEncoder bcryptEncoder;

  @Autowired
  private UserRepository userDao;

  @Autowired
  private RoleRepository roleRepository;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    return new CustomUser(
      user.getUsername(),
      user.getPassword(),
      enabled,
      accountNonExpired,
      credentialsNonExpired,
      accountNonLocked,
      getAuthority(user),
      user.getId());
  }

  private List<SimpleGrantedAuthority> getAuthority(User user) {
    Set<Role> userRoles = user.getRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (Role r : userRoles) {
      authorities.add(new SimpleGrantedAuthority(r.getName()));
    }
    return authorities;
  }

  public List<User> findAll() {
    List<User> list = new ArrayList<>();
    userDao.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  public void delete(int id) {
    userDao.deleteById(id);
  }

  public User findOne(String username) {
    return userDao.findByUsername(username);
  }

  public User findById(Integer id) {
    return userDao.findById(id).get();
  }


//  public UserDTO update(UserDTO userDto) {
//
//    User user = findById(userDto.getId());
//    if (user != null) {
//      BeanUtils.copyProperties(userDto, user, "password");
//
//      Set<Role> roles = user.getRoles();
//
//      switch (userDto.getRole()) {
//
//        case "Rektorat":
//
//          roles.add(roleRepository.findByName("ROLE_VIEWER_ADMIN"));
//          break;
//        case "O'quv-uslubiy bo'lim":
//          roles.add(roleRepository.findByName("ROLE_ADMIN"));
//          break;
//        case "Xodimlar bo'lim":
//          roles.add(roleRepository.findByName("ROLE_STAFF_ADMIN"));
//          break;
//        case "Dekanat":
//          roles.add(roleRepository.findByName("ROLE_DEAN_ADMIN"));
//          break;
//        case "Sifat nazorati":
//          roles.add(roleRepository.findByName("ROLE_QUALITY_ADMIN"));
//          break;
//        case "User":
//          roles.add(roleRepository.findByName("ROLE_USER"));
//
//          break;
//      }
//
//      user.setRoles(roles);
//      userDao.save(user);
//    }
//    return userDto;
//  }


  public User save(UserDTO user) {

    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
    newUser.setEmail(user.getEmail());
    Set<Role> roles = new HashSet<>();

    switch (user.getRole()) {

      case "Admin":
        roles.add(roleRepository.findByName("ROLE_SUPER_ADMIN"));
        break;
      case "User":
        roles.add(roleRepository.findByName("ROLE_USER"));
        break;
    }

    newUser.setRole(user.getRole());
    newUser.setRoles(roles);

    return userDao.save(newUser);


  }
}

