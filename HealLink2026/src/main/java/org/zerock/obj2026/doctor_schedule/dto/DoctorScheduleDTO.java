package org.zerock.obj2026.doctor_schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;       // For work_date
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DoctorScheduleDTO {
    private Long scheduleId;
    private Long doctorId; // References DoctorDTO's doctorId
    private Date workDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAvailable; // TINYINT(1) can map to Boolean
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
