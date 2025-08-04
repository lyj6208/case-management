package com.testing_company.case_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="departments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "department不可為空")
    @Column(name="department")
    private String department;

    @NotBlank(message = "abbreviation不可為空")
    @Size(min=2, max=2, message = "部門代碼必須是兩個大寫英文字母")
    @Pattern(regexp = "^[A-Z]{2}$",message = "部門代碼必須是兩個大寫英文字母")
    @Column(name="abbreviation")
    private String abbreviation;
}
