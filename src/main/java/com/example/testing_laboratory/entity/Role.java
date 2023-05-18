package com.example.testing_laboratory.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    HEAD_OF_DEPARTMENT,
    DEPARTMENT_WORKER,
    SERVICE_ENGINEER,
    AUDITOR;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
