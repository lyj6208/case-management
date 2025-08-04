package com.testing_company.case_management.model;

import com.testing_company.case_management.enums.Sex;
import com.testing_company.case_management.enums.SystemRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name不可為空")
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @NotBlank(message = "idNumber不可為空")
    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email_private")
    private String emailPrivate;

    @Column(name = "email_company")
    private String emailCompany;

    @Column(name = "employee_number")
    private String employeeNumber;


    @Column(name = "job_level_id")
    private Long jobLevelId;


    @Column(name = "team_id")
    private Long teamId;

    @NotNull(message = "systemRole不可為空")
    @Enumerated(EnumType.STRING)
    @Column(name = "system_role")
    private SystemRole systemRole;

    @Column(name = "hired_at")
    private LocalDate hiredAt;

    @Column(name="last_modified_by_id")
    private Long lastModifiedById;



}
