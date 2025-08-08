package com.testing_company.case_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
public class TeamResponseDTO {
    @JsonProperty("部門")
    private String department;
    @JsonProperty("組別")
    private String team;

    public TeamResponseDTO(String department, String team){
        this.department=department;
        this.team=team;
    }
}
