package com.testing_company.case_management.enums;

public enum CaseStatus {
    CASE_CREATED("已立案"),
    IN_PROGRESS("實驗中"),
    DOUBLECHECK("複驗中"),
    UNDER_REVIEW("審核中"),
    REPORT_IN_PREPARATION("結果報告製作中"),
    CLOSED("已結案"),
    CANCELED("已取消");

    private final String displayName;

    CaseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
