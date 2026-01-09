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
    private Long hospitalId;
    private String name;
    private String address;
    private String phone;
    private BigDecimal latitude; // Using BigDecimal for precision
    private BigDecimal longitude; // Using BigDecimal for precision
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
