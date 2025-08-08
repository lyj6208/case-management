package com.testing_company.case_management.dto;

import java.time.LocalDateTime;

public interface TestCaseWithTestItemDTO {
    // test_cases 資料
    Long getTcId();
    String getTestCaseNumber();
    Long getTcTestItemId();
    LocalDateTime getCaseStartTime();
    LocalDateTime getLabDeadline();
    LocalDateTime getReportDeadline();
    Long getExperimentOperatorId();
    Long getExperimentReviewerId();
    Long getReportConductorId();
    String getSampleName();
    String getCaseStatus();

    // test_items 資料
    Long getTiId();
    Long getTiTeamId();
}
