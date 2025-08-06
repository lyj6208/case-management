package com.testing_company.case_management.dto;
import com.testing_company.case_management.enums.Sex;
import com.testing_company.case_management.model.User;
import com.testing_company.case_management.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "name不可為空")
    private String name;
    @Enumerated(EnumType.STRING)

    private Sex sex;

    @NotBlank(message = "idNumber不可為空")
    private String idNumber;

    private LocalDate birthday;

    private String phone;

    private String emailPrivate;

    private String emailCompany;

    private String employeeNumber;

    private Long jobLevelId;

    private Long teamId;

    private Role role;

    @Column(name = "hired_at")
    private LocalDate hiredAt;

    private Long lastModifiedById;
}
