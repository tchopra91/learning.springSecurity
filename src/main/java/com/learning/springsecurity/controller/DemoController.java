package com.learning.springsecurity.controller;

import java.util.ArrayList;
import java.util.List;

import com.learning.springsecurity.entity.RoleEnum;
import com.learning.springsecurity.entity.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoController {

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @GetMapping("/")
    public String showLanding() {
        return "landing";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/leaders")
    public String showLeaders() {
        return "leaders";
    }

    @GetMapping("/admins")
    public String showAdmins() {
        return "admins";
    }

    @GetMapping("/accessDenied")
    public String showAccessDenied() {
        return "accessDenied";
    }

    @GetMapping("/show-registration-form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDetails());

        model.addAttribute("allRoles", RoleEnum.values());

        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@ModelAttribute("user") UserDetails userDetails) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleEnum role : userDetails.getRoles())
            authorities.add(new SimpleGrantedAuthority(role.getRoleStr()));

        String password = userDetails.getPassword();
        password = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);

        User user = new User(userDetails.getUsername(), password, authorities);

        jdbcUserDetailsManager.createUser(user);

        return "registration-confirmation";
    }
}