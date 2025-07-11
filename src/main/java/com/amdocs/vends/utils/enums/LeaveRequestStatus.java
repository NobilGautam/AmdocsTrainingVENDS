package com.amdocs.vends.utils.enums;

public enum LeaveRequestStatus {
    APPROVED("approved"),
    PENDING("pending");

    public final String value;

    LeaveRequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
