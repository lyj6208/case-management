package com.testing_company.case_management.repository;

import com.testing_company.case_management.model.IndustryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryCategoryRepository extends JpaRepository<IndustryCategory, Long> {
}
