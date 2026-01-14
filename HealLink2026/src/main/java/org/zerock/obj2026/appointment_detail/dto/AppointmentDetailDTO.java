package org.zerock.obj2026.appointment_detail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentDetailDTO {
    private Long appointmentId; //  AppointmentDTO에서 appointmentId를 참조
    private Long departmentId; //  DepartmentDTO에서 departmentId를 참조
    private String symptom;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
