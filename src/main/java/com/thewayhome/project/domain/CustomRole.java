package com.thewayhome.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomRole {
    // 본사 권한
    CUSTOMERA("ROLE_A"),
    CUSTOMERB("ROLE_B"),
    CUSTOMERC("ROLE_C");

    private String value;
}