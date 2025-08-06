package com.testing_company.case_management.enums;

public enum Role {
    USER("使用者"),
    CASE_HANDLE_MEMBER("接案組-組員"),
    LAB_MEMBER("實驗組-組員"),
    LAB_LEADER("實驗組-組長"),
    LAB_MANAGER("實驗組-經理"),
    REPORT_MEMBER("報告組-組員"),
    HR_MEMBER("人事處_組員"),
    ADMIN("最高權限者"),
    RESIGNED("離職"),
    SUSPENDED("停權");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
