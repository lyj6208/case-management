package com.testing_company.case_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testing_company.case_management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindTestCaseByCustomRequestDTO {
    private LocalDate caseStartDate=LocalDate.now();
    private LocalDateTime caseStartDateTime=caseStartDate.atStartOfDay();
    private LocalDate caseEndDate=LocalDate.now();
    private LocalDateTime caseEndDateTime=caseEndDate.plusDays(1).atStartOfDay();
    private List<String> caseStatus=new ArrayList<>();
    private Long teamId=null;
    private Boolean isMine=false;
    private Long experiment_operator_id;
    private Long experiment_reviewer_id;
    private Long report_conductor_id;

    public LocalDateTime getCaseStartDateTime(){
        return caseStartDate!=null?caseStartDate.atStartOfDay():caseStartDateTime;
    }
    public LocalDateTime getCaseEndDateTime(){
        return caseEndDate!=null?caseEndDate.plusDays(1).atStartOfDay():caseEndDateTime;
    }
}
