package com.cyberComet.codefellowship.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  SiteUserDetailsServiceImplementation siteUserDetailsServiceImplementation;

  @Bean
    public PasswordEncoder passwordEncoder(){
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
  }

  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(siteUserDetailsServiceImplementation).passwordEncoder(passwordEncoder());
  }

  @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
              .cors().disable()
              .csrf().disable()

              .authorizeRequests()
              .antMatchers("/", "/login", "/signup", "/*.css", "/images/**").permitAll()
              .anyRequest().authenticated()
              .and()

              .formLogin()
              .loginPage("/login")

              .and()
              .logout()
              .logoutSuccessUrl("/login");
  }

}
