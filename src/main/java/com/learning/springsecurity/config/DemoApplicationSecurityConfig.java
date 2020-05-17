package com.learning.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class DemoApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication().withUser(users.username("eric").password("x").roles("user", "employee"))
                .withUser(users.username("mary").password("x").roles("user", "employee", "manager"))
                .withUser(users.username("admin").password("x").roles("user", "admin"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/home").hasRole("user")
                .antMatchers("/leaders").hasRole("manager").antMatchers("/admins").hasRole("admin").and().formLogin()
                .defaultSuccessUrl("/home").loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/")
                .permitAll().and().exceptionHandling().accessDeniedPage("/accessDenied");
    }
}