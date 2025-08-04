package com.testing_company.case_management.enums;

public enum SystemRole {
    CASE_HANDLER("接案人員"),
    LAB_LEADER("實驗人員-組長"),
    LAB_MEMBER("實驗人員-組員"),
    LAB_MANAGER("實驗人員-經理"),
    REPORTER("報告人員"),
    HR("人資處人員"),
    ADMIN("最高權限者"),
    RESIGNED("離職"),
    SUSPENDED("停權");

    private final String displayName;

    SystemRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
