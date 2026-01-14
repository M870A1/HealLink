package org.zerock.obj2026.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PatientDTO {
    private Long patientId; // References UserDTO's userId
    private String phone;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private String bloodType;
    private Boolean hasAllergies; // TINYINT(1) can map to Boolean
    private String allergies;
    private String medicalHistory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
