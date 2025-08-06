package com.testing_company.case_management.repository;

import com.testing_company.case_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT MAX(u.employeeNumber) FROM User u where u.employeeNumber LIKE :prefix")
    String findMaxEmployeeNumberByPrefix(@Param("prefix")String prefix);
    Optional<User>findByUsername(String username);
    Boolean existsByIdNumber(String idNumber);

}

