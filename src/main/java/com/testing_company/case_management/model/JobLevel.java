package com.testing_company.case_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "jobLevel不可為空")
    @Column(name="job_level")
    private String jobLevel;
}
