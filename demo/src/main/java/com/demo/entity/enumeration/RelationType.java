package com.demo.entity.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RelationType {
    ONE_TO_ONE("ONE_TO_ONE"),
    ONE_TO_MANY("ONE_TO_MANY"),
    MANY_TO_ONE("MANY_TO_ONE");

    private final String value;
}
