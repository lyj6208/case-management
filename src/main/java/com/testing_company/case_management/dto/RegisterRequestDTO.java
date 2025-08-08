package com.testing_company.case_management.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
//    @JsonProperty("姓名")
    private String name;
    @Enumerated(EnumType.STRING)
//    @JsonProperty("性別")
    private Sex sex;

    @NotBlank(message = "idNumber不可為空")
//    @JsonProperty("身分證字號")
    private String idNumber;
//    @JsonProperty("生日")
    private LocalDate birthday;
//    @JsonProperty("電話")
    private String phone;
//    @JsonProperty("私人信箱")
    private String emailPrivate;
//    @JsonProperty("公司信箱")
    private String emailCompany;
//    @JsonProperty("員工編號")
    private String employeeNumber;
//    @JsonProperty("職級")
    private Long jobLevelId;
//    @JsonProperty("部門系統id")
    private Long teamId;
//    @JsonProperty("系統角色")
    private Role role;

    @Column(name = "hired_at")
    private LocalDate hiredAt;

    private Long lastModifiedById;
}
