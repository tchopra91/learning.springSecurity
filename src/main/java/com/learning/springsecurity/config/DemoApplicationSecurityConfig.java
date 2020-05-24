package com.learning.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class DemoApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource);

        // UserBuilder users = User.withDefaultPasswordEncoder();

        // auth.inMemoryAuthentication().withUser(users.username("eric").password("x").roles("user",
        // "employee"))
        // .withUser(users.username("mary").password("x").roles("user", "employee",
        // "manager"))
        // .withUser(users.username("admin").password("x").roles("user", "admin"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/home").hasAuthority("user")
                .antMatchers("/leaders").hasAuthority("manager").antMatchers("/admins").hasAuthority("admin").and()
                .formLogin().defaultSuccessUrl("/home").loginPage("/login").permitAll().and().logout()
                .logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);

        return jdbcUserDetailsManager;
    }
}