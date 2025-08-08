package com.testing_company.case_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TestItemResponseDTO {
    @JsonProperty("測試項目系統id")
    private Long id;
    @JsonProperty("測試項目名稱")
    private String name;
    @JsonProperty("負責部門")
    private String department;
    @JsonProperty("負責組別")
    private String team;
    @JsonProperty("執行天數")
    private Integer testingDays;
    @JsonProperty("價錢")
    private BigDecimal testingPrice;
    @JsonProperty("建立時間")
    private Timestamp createdTime;
    @JsonProperty("最後修改時間")
    private Timestamp lastModifiedTime;
    @JsonProperty("最後修改者")
    private String lastModifiedBy;
    @JsonProperty("實驗執行人員")
    private String experimentOperator;
    @JsonProperty("實驗審核人員")
    private String experimentReviewer;
    @JsonProperty("報告製作人員")
    private String reportConductor;
}
