package com.testing_company.case_management.model;

import com.testing_company.case_management.enums.CaseStatus;
import com.testing_company.case_management.enums.SampleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="test_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestItem extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="team_id")
    private Long teamId;
    @Column(name="testing_days")
    private Integer testingDays;
    @Column(name="testing_price")
    private BigDecimal testingPrice;
    @Column(name="last_modified_by_id")
    private Long lastModifiedById;

}
