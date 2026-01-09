package org.zerock.obj2026.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DoctorDTO {
    private Long doctorId; // References UserDTO's userId
    private Long hospitalId; // References HospitalDTO's hospitalId
    private String licenseNumber;
    private Integer careerYears;
    private String introduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
