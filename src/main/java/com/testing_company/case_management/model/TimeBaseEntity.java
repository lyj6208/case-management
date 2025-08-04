package com.testing_company.case_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@MappedSuperclass
@Data
public abstract class TimeBaseEntity {

    @CreationTimestamp
    @Column(name="created_time", updatable = false)
    private Timestamp createdTime;

    @UpdateTimestamp
    @Column(name="last_modified_time")
    private Timestamp lastModifiedTime;
}
