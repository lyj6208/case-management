package com.testing_company.case_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
public class TeamResponseDTO {
    private String department;
    private String team;

    public TeamResponseDTO(String department, String team){
        this.department=department;
        this.team=team;
    }
}
