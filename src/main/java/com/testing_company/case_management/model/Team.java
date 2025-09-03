package com.testing_company.case_management.model;

import com.testing_company.case_management.validation.Chinese;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="teams")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "departmentId不可為空")
    @Column(name="department_id")
    private Long departmentId;


    @NotBlank(message = "team不可為空")
    @Column(name="team")
    @Chinese
    private String teamNameInChinese;
}
