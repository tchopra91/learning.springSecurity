package com.learning.springsecurity.entity;

import java.util.ArrayList;
import java.util.List;

public class UserDetails {

    private String username;
    private String password;
    private boolean enabled = false;

    private List<RoleEnum> roles = new ArrayList<RoleEnum>() {
        {
            add(RoleEnum.USER);
        }
    };

    public UserDetails() {
    }

    public UserDetails(String username, String password, boolean enabled, List<RoleEnum> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnum> roles) {
        this.roles = roles;
    }
}