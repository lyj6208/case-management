package com.testing_company.case_management.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TestItemResponseDTO {
    private Long id;
    private String name;
    private String department;
    private String team;
    private Integer testingDays;
    private BigDecimal testingPrice;
    private Timestamp createdTime;
    private Timestamp lastModifiedTime;
    private String lastModifiedBy;
}
