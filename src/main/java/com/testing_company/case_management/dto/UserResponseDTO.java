package com.testing_company.case_management.dto;

import com.testing_company.case_management.enums.Sex;
import com.testing_company.case_management.enums.SystemRole;
import com.testing_company.case_management.model.User;
import com.testing_company.case_management.repository.JobLevelRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor

public class UserResponseDTO {

    private Long id;
    private String name;
    private Sex sex;
    private String idNumber;
    private LocalDate birthday;
    private String phone;
    private String emailPrivate;
    private String emailCompany;
    private String employeeNumber;
    private String jobLevel;
    private String department;
    private String team;
    private SystemRole systemRole;
    private LocalDate hiredAt;
    private Timestamp createdTime;
    private Timestamp lastModifiedTime;
    private String lastModifiedBy;
}
