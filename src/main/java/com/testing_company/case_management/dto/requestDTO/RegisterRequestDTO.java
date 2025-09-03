package com.testing_company.case_management.dto.requestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.testing_company.case_management.enums.Sex;
import com.testing_company.case_management.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "name不可為空")
    @JsonProperty("姓名")
    private String name;
    @Enumerated(EnumType.STRING)
    @JsonProperty("性別")
    private Sex sex;
    @NotBlank(message = "idNumber不可為空")
    @JsonProperty("身分證字號")
    private String idNumber;
    @JsonProperty("生日")
    private LocalDate birthday;
    @JsonProperty("手機號碼")
    private String phone;
    @JsonProperty("私人信箱")
    private String emailPrivate;
    @JsonProperty("職級id")
    private Long jobLevelId;
    @JsonProperty("組別id")
    private Long teamId;
    @JsonProperty("系統角色")
    private Role role;
    @JsonProperty("聘僱日")
    private LocalDate hiredAt;
}
