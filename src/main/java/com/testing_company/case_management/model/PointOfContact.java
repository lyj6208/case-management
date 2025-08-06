package com.testing_company.case_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="point_of_contact")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointOfContact extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "customer_id不得為空")
    @Column(name="customer_id")
    private Long customerId;

    @NotBlank(message = "contact_person不得為空")
    @Column(name="contact_person")
    private String contactPerson;
    @Column(name="contact_phone")
    private String contactPhone;
    @Column(name="contact_email")
    private String contactEmail;
    @Column(name="contact_address")
    private String contactAddress;
    private Long lastModifiedById;
}
