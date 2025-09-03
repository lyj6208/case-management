package com.testing_company.case_management.dto.requestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testing_company.case_management.validation.Chinese;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestDTO {
//    @NotBlank(message = "Department名稱不得為空")
    @Chinese
    @JsonProperty("部門名稱(中文)")
    public String departmentNameInChinese;

//    @NotBlank(message = "abbreviation不可為空")
    @Size(min=2, max=2, message = "部門代碼必須是兩個大寫英文字母")
    @Pattern(regexp = "^[A-Z]{2}$",message = "部門代碼必須是兩個大寫英文字母")
    @JsonProperty("部門縮寫(英文)")
    private String abbreviationInEnglish;
}
