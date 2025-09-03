package com.testing_company.case_management.dto.requestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobLevelRequestDTO {
    @NotBlank(message="職級名稱(中文)欄位不得為空")
    @JsonProperty("職級名稱(中文)")
    public String jobLevelNameInChinese;
}


