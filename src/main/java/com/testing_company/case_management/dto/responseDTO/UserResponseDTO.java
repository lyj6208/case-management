package com.testing_company.case_management.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testing_company.case_management.enums.Role;
import com.testing_company.case_management.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor

public class UserResponseDTO {
    @JsonProperty("使用者系統id")
    private Long id;
    @JsonProperty("姓名")
    private String name;
    @JsonProperty("性別")
    private Sex sex;
    @JsonProperty("身分證字號號")
    private String idNumber;
    @JsonProperty("生日")
    private LocalDate birthday;
    @JsonProperty("電話")
    private String phone;
    @JsonProperty("私人信箱")
    private String emailPrivate;
    @JsonProperty("公司信箱")
    private String emailCompany;
    @JsonProperty("員工編號")
    private String employeeNumber;
    @JsonProperty("職級")
    private String jobLevel;
    @JsonProperty("部門")
    private String department;
    @JsonProperty("組別")
    private String team;
    @JsonProperty("系統角色")
    private Role role;
    @JsonProperty("聘僱起始日")
    private LocalDate hiredAt;
    @JsonProperty("建立時間")
    private Timestamp createdTime;
    @JsonProperty("最後修改時間")
    private Timestamp lastModifiedTime;
    @JsonProperty("最後修改者")
    private String lastModifiedBy;
}
