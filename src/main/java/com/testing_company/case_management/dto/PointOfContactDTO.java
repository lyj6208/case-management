package com.testing_company.case_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class PointOfContactDTO {
//    @JsonProperty("聯絡人之系統id")
    Long id;
//    @JsonProperty("聯絡人之公司id")
    private Long customerId;
//    @JsonProperty("聯絡人公司名稱")
    private String customerName;
//    @JsonProperty("聯絡人姓名")
    private String contactPerson;
//    @JsonProperty("聯絡人電話")
    private String contactPhone;
//    @JsonProperty("聯絡人Email")
    private String contactEmail;
//    @JsonProperty("聯絡人地址")
    private String contactAddress;
//    @JsonProperty("建立時間")
    private Timestamp createdTime;
//    @JsonProperty("最後修改時間")
    private Timestamp lastModifiedTime;
//    @JsonProperty("最後修改者")
    private String lastModifiedBy;
}
