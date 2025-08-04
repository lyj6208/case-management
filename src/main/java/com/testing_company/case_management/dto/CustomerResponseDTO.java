package com.testing_company.case_management.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class CustomerResponseDTO {

    private Long id;
    private Boolean isCompany;
    private String name;
    private String industryCategory;
    private String address;
    private String phone;
    private Timestamp createdTime;
    private Timestamp lastModifiedTime;
    private String lastModifiedBy;
}
