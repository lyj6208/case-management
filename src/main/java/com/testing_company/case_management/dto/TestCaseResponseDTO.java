package com.testing_company.case_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testing_company.case_management.enums.CaseStatus;
import com.testing_company.case_management.enums.SampleStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TestCaseResponseDTO {
    @JsonProperty("案件系統邊號")
    private Long id;
    @JsonProperty("客戶")
    private String customer;
    @JsonProperty("案件邊號")
    private String testCaseNumber;
    @JsonProperty("客戶_聯絡人")
    private String pointOfContact_person;
    @JsonProperty("客戶_聯絡Email")
    private String pointOfContact_email;
    @JsonProperty("客戶_聯絡電話")
    private String pointOfContact_phone;
    @JsonProperty("客戶_聯絡地址")
    private String pointOfContact_address;
    @JsonProperty("樣品名稱")
    private String sampleName;
    @JsonProperty("測試項目名稱")
    private String testItem_name;
    @JsonProperty("執行天數")
    private Integer testingDays;
    @JsonProperty("價格")
    private BigDecimal testingPrice;
    @JsonProperty("負責部門")
    private String department;
    @JsonProperty("負責組別")
    private String team;
    @JsonProperty("案件正式啟動日")
    private Timestamp caseStartTime;
    @JsonProperty("實驗執行實際完成時間")
    private Timestamp experimentEndTime;
    @JsonProperty("實驗審核實際完成日期")
    private Timestamp experimentReviewTime;
    @JsonProperty("報告製作實際完成時間")
    private Timestamp reportCloseTime;
    @JsonProperty("實驗執截止時間")
    private Timestamp labDeadline;
    @JsonProperty("報告截止時間")
    private Timestamp reportDeadline;
    @JsonProperty("實驗執行人員")
    private String experimentOperator;
    @JsonProperty("實驗審核人員")
    private String experimentReviewer;
    @JsonProperty("報告執行人員")
    private String reportConductor;
    @JsonProperty("測試結果")
    private String testResult;

    private String caseStatus;
    private String sampleStatus;

    private double sampleOriginalWeight;
    private double sampleRemainingWeight;

    private String caseHandler;
    private String note;
    private Timestamp createdTime;
    private Timestamp lastModifiedTime;

    private String lastModifiedBy;
}
