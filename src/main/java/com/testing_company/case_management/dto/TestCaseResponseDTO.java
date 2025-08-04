package com.testing_company.case_management.dto;

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
    private Long id;
    private String customer;
    private String testCaseNumber;
    private String pointOfContact_person;
    private String pointOfContact_email;
    private String pointOfContact_phone;
    private String pointOfContact_address;

    private String sampleName;

    private String testItem_name;
    private Integer testingDays;
    private BigDecimal testingPrice;
    private String department;
    private String team;

    private Timestamp caseStartTime;
    private Timestamp experimentEndTime;
    private Timestamp experimentReviewTime;
    private Timestamp reportCloseTime;

    private Timestamp labDeadline;
    private Timestamp reportDeadline;
    private String experimentConductor;
    private String experimentReviewer;
    private String reportConductor;
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
