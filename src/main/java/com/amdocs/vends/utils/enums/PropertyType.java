package com.amdocs.vends.utils.enums;

public enum PropertyType {
    VILLA("villa"),
    FLAT("flat"),
    PLOTTED_HOUSE("plotted house");

    private final String value;

    PropertyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
