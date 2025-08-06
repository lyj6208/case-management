package com.testing_company.case_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message="使用者名稱不能為空")
    String username;
    @NotBlank(message = "密碼不能為空")
    String password;
}
