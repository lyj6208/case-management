package com.testing_company.case_management.enums;

public enum Sex {
    MALE("男性"),
    FEMALE("女性");

    private final String displayName;

    Sex(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
