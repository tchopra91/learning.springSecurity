package com.learning.springsecurity.entity;

public enum RoleEnum {
    USER("user"), EMPLOYEE("employee"), MANAGER("manager"), ADMIN("admin");

    private String roleStr;

    RoleEnum(String roleStr) {
        this.roleStr = roleStr;
    }

    public String getRoleStr() {
        return roleStr;
    }
}