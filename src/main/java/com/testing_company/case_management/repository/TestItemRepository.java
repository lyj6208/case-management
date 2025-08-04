package com.testing_company.case_management.repository;

import com.testing_company.case_management.model.TestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestItemRepository extends JpaRepository<TestItem, Long> {
}
