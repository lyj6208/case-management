package com.testing_company.case_management.model;

import com.testing_company.case_management.enums.CaseStatus;
import com.testing_company.case_management.enums.SampleStatus;
import com.testing_company.case_management.enums.SystemRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="test_cases")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCase extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="test_case_number")
    private String testCaseNumber;
    @NotNull(message = "customerId不可為空")
    @Column(name="customer_id")
    private Long customerId;
    @NotNull(message = "pointOfContactId不可為空")
    @Column(name="point_of_contact_id")
    private Long pointOfContactId;

    @NotBlank(message = "sampleName不可為空")
    @Column(name="sample_name")
    private String sampleName;

    @NotNull(message = "testItemId不可為空")
    @Column(name="test_item_id")
    private Long testItemId;
    @Column(name="testing_days")
    private Integer testingDays;
    @Column(name="testing_price")
    private BigDecimal testingPrice;

    @Column(name="case_start_time")
    private Timestamp caseStartTime;
    @Column(name="experiment_end_time")
    private Timestamp experimentEndTime;
    @Column(name="experiment_review_time")
    private Timestamp experimentReviewTime;
    @Column(name="report_close_time")
    private Timestamp reportCloseTime;

    @Column(name="lab_deadline")
    private Timestamp labDeadline;
    @Column(name="report_deadline")
    private Timestamp reportDeadline;

    @Column(name="experiment_conductor_id")
    private Long experimentConductorId;
    @Column(name="experiment_reviewer_id")
    private Long experimentReviewerId;
    @Column(name="report_conductor_id")
    private Long reportConductorId;


    @Column(name="test_result")
    private String testResult;

    @Enumerated(EnumType.STRING)
    @Column(name="case_status")
    private CaseStatus caseStatus;
    @Enumerated(EnumType.STRING)
    @Column(name="sample_status")
    private SampleStatus sampleStatus;

    @Column(name="sample_original_weight")
    private double sampleOriginalWeight;
    @Column(name="sample_remaining_weight")
    private double sampleRemainingWeight;

    @Column(name="case_handler_id")
    private Long caseHandlerId;
    @Column(name="note")
    private String note;

    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

}
