package com.testing_company.case_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class TestCaseWithTestItemResponseDTO {
    @JsonProperty("案件邊號")
    public String testCaseNumber;
    @JsonProperty("樣品名稱")
    public String sampleName;
    @JsonProperty("測試項目名稱")
    public String testItem;
    @JsonProperty("案件正式啟動日")
    public LocalDateTime caseStartTime;
    @JsonProperty("實驗室截止日")
    public LocalDateTime labDeadline;
    @JsonProperty("報告截止日")
    public LocalDateTime reportDeadline;
    @JsonProperty("實驗操作人員")
    public String experimentOperator;
    @JsonProperty("實驗審核人員")
    public String experimentReviewer;
    @JsonProperty("報告製作人員")
    public String reportConductor;
    @JsonProperty("案件狀態")
    public String caseStatus;
    @JsonProperty("組別")
    public String team;
}
