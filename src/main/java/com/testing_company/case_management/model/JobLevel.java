package com.testing_company.case_management.model;

import com.testing_company.case_management.validation.Chinese;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="job_level")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]+$", message = "職位名稱必須為中文")
    @NotBlank(message = "jobLevel不可為空")
    @Column(name="job_level")
    @Chinese
    private String jobLevelNameInChinese;
}
