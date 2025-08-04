package com.testing_company.case_management.repository;

import com.testing_company.case_management.model.TestCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.tags.form.SelectTag;

import java.sql.Timestamp;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    @Query(value = "SELECT MAX(CAST(SUBSTRING(test_case_number, 3) AS UNSIGNED)) " +
            "FROM test_cases", nativeQuery = true)
    public Long findMaxCaseNumber();

    Page<TestCase>findByCaseStartTimeBetween(Timestamp begin, Timestamp end, Pageable pageable);
}
