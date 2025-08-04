package com.testing_company.case_management.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class PointOfContactDTO {

    Long id;

    private Long customerId;
    private String customerName;

    private String contactPerson;

    private String contactPhone;

    private String contactEmail;

    private String contactAddress;

    private Timestamp createdTime;

    private Timestamp lastModifiedTime;

    private String lastModifiedBy;
}
