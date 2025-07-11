package com.amdocs.vends.utils.enums;

public enum Role {
    ADMIN("admin"),
    TENANT("tenant");

    public final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
