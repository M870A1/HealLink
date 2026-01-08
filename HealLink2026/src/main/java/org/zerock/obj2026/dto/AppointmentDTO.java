package org.zerock.obj2026.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentDTO {
    private Long appointmentId;
    private Long patientId; // References PatientDTO's patientId
    private Long scheduleId; // References DoctorScheduleDTO's scheduleId
    private AppointmentStatus status; // Using the enum
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
