package com.amdocs.vends.utils.enums;

public enum RequestStatus {
    APPROVED("approved"),
    PENDING("pending");

    public final String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
