package com.testing_company.case_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="industry_categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndustryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "industryCategory不得為空")
    @Column(name="industry_category")
    private String industryCategory;
}
