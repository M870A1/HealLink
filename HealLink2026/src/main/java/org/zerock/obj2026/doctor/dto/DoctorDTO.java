package org.zerock.obj2026.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DoctorDTO {
    private Long doctorId; // UserDTO에서  userId를 참조
    private Long hospitalId; // HospitalDTO에서 hospitalId를 참조
    private String licenseNumber;
    private Integer careerYears;
    private String introduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
