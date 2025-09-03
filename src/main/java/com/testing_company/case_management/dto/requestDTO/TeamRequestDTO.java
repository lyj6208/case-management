package com.testing_company.case_management.dto.requestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testing_company.case_management.validation.Chinese;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDTO {
    @JsonProperty("部門id")
    private Long departmentId;
    @JsonProperty("組別名稱(中文)")
    private String teamNameInChinese;
}
