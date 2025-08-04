package com.testing_company.case_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="customers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_company")
    private Boolean isCompany;
    @NotBlank(message = "name不可為空")
    @Column(name="name")
    private String name;
    @Column(name="industry_category_id")
    private Long industryCategoryId;
    @Column(name="address")
    private String address;
    @Column(name="phone")
    private String phone;
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;
}