package com.testing_company.case_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class CustomerResponseDTO {
//    @JsonProperty("客戶系統id")
    private Long id;
//    @JsonProperty("是否為公司")
    private Boolean isCompany;
//    @JsonProperty("名稱")
    private String name;
//    @JsonProperty("產業類別")
    private String industryCategory;
//    @JsonProperty("地址")
    private String address;
//    @JsonProperty("電話")
    private String phone;
//    @JsonProperty("建立時間")
    private Timestamp createdTime;
//    @JsonProperty("最後修改時間")
    private Timestamp lastModifiedTime;
//    @JsonProperty("最後修改者")
    private String lastModifiedBy;
}
