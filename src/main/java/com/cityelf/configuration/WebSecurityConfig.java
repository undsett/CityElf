package com.cityelf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable() //this line disables requests for CSRF tokens
        .authorizeRequests()
        .antMatchers("/", "/home").permitAll()
        //.antMatchers("/advertisements/admin/**", "/polls/admin/**").hasAuthority("ADMIN_ROLE")
        //this line prevents access with out login
        //.anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .permitAll();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .jdbcAuthentication()
        .usersByUsernameQuery(
            "select u.email as username, u.password, true as enabled from users u "
                + "where u.email=?")
        .authoritiesByUsernameQuery(
            "select u.email as username, r.name as role from users u "
                + "join user_role ur "
                + "join roles r "
                + "on r.id=ur.role_id and u.id=ur.user_id "
                + "where u.email=?");
  }
}
