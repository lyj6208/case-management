package com.testing_company.case_management.enums;

public enum SampleStatus {
    AWAITING_DELIVERY("等待送達"),
    PENDING_TESTING_AREA("待驗區"),
    LAB_COLLECTED("實驗室領出中"),
    STORAGE_AREA("保存區"),
    DESTROYED("已銷毀");

    private final String displayName;

    SampleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
