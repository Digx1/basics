package com.study.basics.enums;

public enum UserSearchField {
    NAME("name"),
    EMAIL("email"),
    ROLE("role"),
    COMPANY("company");

    private String code;

    UserSearchField(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
