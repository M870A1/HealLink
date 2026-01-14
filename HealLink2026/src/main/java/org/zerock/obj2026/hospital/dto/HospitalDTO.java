package org.zerock.obj2026.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // For DECIMAL types
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HospitalDTO {
    private String hpid;
    private String dutyName;
    private String dutyAddr;
    private String dutyTel1;
    private BigDecimal wgs84Lat;
    private BigDecimal wgs84Lon;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
