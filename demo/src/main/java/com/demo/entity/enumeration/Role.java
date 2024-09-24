package com.demo.entity.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Role {
    GROUP_ADMIN("GROUP_ADMIN"),
    GROUP_DEPUTY("GROUP_DEPUTY"),
    GROUP_USER("GROUP_USER"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    public static final List<Role> GROUP_ROLES = List.of(GROUP_ADMIN, GROUP_DEPUTY, GROUP_USER);
}
