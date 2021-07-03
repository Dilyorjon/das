package uz.polito.das.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.polito.das.dto.UserDTO;

import uz.polito.das.model.User;
import uz.polito.das.service.UserService;

import java.util.List;


import static uz.polito.das.config.Constants.*;


@CrossOrigin(origins = HTTP_LINK, maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "users", method = RequestMethod.GET)
  public List listUser() {
    return userService.findAll();
  }


  @RequestMapping(value = "signup", method = RequestMethod.POST)
  public User saveUser(@RequestBody UserDTO user) {
    return userService.save(user);
  }


  @RequestMapping(method = RequestMethod.PUT, value = "user")
  public void updateUser(@RequestBody UserDTO user) {
    userService.update(user);
  }


}

