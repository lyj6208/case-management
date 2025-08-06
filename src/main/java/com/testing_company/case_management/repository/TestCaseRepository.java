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
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    @Query(value = "SELECT MAX(CAST(SUBSTRING(test_case_number, 3) AS UNSIGNED)) " +
            "FROM test_cases", nativeQuery = true)
    public Long findMaxCaseNumber();

    Page<TestCase>findByCaseStartTimeBetween(Timestamp begin, Timestamp end, Pageable pageable);

    @Query(value= """
            SELECT*FROM test_cases tc
            JOIN test_items ti ON tc.test_item_id=ti.id
            WHERE (:start IS NULL OR tc.case_start_time>= :start)
            AND (:end IS NULL OR tc.case_start_time<= :end)
            AND (:statuses IS NULL OR tc.case_status IN ( :statuses))
            AND (:teamId IS NULL OR ti.team_id= :teamId)
            """, nativeQuery = true)
    List<TestCase>findByCustom2(@Param("start")LocalDateTime caseStartTime,
                                @Param("end")LocalDateTime caseEndTime,
                                @Param("statuses")List<String> caseStatus,
                                @Param("teamId")Long teamId);
}
