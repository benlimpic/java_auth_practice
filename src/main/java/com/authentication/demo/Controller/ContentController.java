package com.authentication.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.authentication.demo.logger.AuthenticationLogger;

@Controller
public class ContentController {

  @GetMapping("/req/login")
  public String login() {
    AuthenticationLogger.logAuthenticationDetails();
    return "login";
  }

  @GetMapping("/req/signup")
  public String signup() {
    AuthenticationLogger.logAuthenticationDetails();
    return "signup";
  }

  @GetMapping("/index")
  public String home() {
    AuthenticationLogger.logAuthenticationDetails();
    return "index";
  }

  @GetMapping("/user")
  public String user() {
    AuthenticationLogger.logAuthenticationDetails();
    return "user";
  }

}
