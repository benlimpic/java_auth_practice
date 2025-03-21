package com.authentication.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.authentication.demo.Service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserService userService;
  private final SecurityHandler securityHandler;

  public SecurityConfig(@Lazy UserService userService, SecurityHandler securityHandler) {
    this.userService = userService;
    this.securityHandler = securityHandler;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return userService;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http// CSRF is enabled by default
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/signup", "/login", "/css/**", "/js/**", "/images/**").permitAll()
              .anyRequest().authenticated())
          .formLogin(form -> form
              .loginPage("/login")
              .permitAll())
          .logout(logout -> logout
              .logoutUrl("/logout")
              .invalidateHttpSession(true)
              .clearAuthentication(true)
              .deleteCookies("JSESSIONID"));
      return http.build();
  }
}