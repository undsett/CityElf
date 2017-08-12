package com.cityelf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/forecasts/startcollector", "/feedback/get/**").hasAuthority("SYSTEM_ROLE")
        .antMatchers("/advertisements/admin/**", "/polls/admin/**").hasAuthority("ADMIN_ROLE")
        .antMatchers(
            "/users/updateUser",
            "/users/*",
            "/advertisements/getall",
            "/advertisements/getadvertisement",
            "/polls/getall",
            "/polls/getpoll",
            "/peoplereport/add",
            "/voting/*",
            "/voting/**"
        ).hasAuthority("AUTHORIZED_ROLE")
        .antMatchers(
            "/waterforecast/**",
            "/electricityforecast/**",
            "/gasforecast/**"
        ).hasAuthority("UTILITYADMIN_ROLE")
        .and()
        .httpBasic()
        .and()
        .csrf().disable();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
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
