package org.zerock.obj2026.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.obj2026.appointment.domain.AppointmentStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentDTO {
    private Long appointmentId;
    private Long patientId; // PatientDTO에서 patientId를 참조함
    private Long scheduleId; // DoctorScheduleDTO에서 scheduleId를 참조함
    private Long departmentId;
    private String symptom;
    private String note;
    private AppointmentStatus appointmentStatus;
    // ↓열거형의 멤버들을 현재 범위(scope)로 가져와서 이름 없이 직접 사용할 수 있게 해주는 기능
    private LocalDateTime createdAt;
    private AppointmentStatus status;
    private LocalDateTime updatedAt;

}
