package uz.polito.das.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.polito.das.config.TokenProvider;
import uz.polito.das.dto.UserDTO;
import uz.polito.das.model.AuthToken;
import uz.polito.das.model.CustomUser;

import static uz.polito.das.config.Constants.*;


@CrossOrigin(origins = HTTP_LINK)
@RestController
@RequestMapping("/api/")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider jwtTokenUtil;

  @RequestMapping(value = "token/generate-token", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserDTO loginUser) throws AuthenticationException {

    final Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginUser.getUsername(),
        loginUser.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    CustomUser u = null;
    if (null != securityContext.getAuthentication()) {
      u = (CustomUser) securityContext.getAuthentication().getPrincipal();
    }
    int id = u.getId();

    final String token = jwtTokenUtil.generateToken(id, authentication);

    return ResponseEntity.ok(new AuthToken(token));
  }

}

