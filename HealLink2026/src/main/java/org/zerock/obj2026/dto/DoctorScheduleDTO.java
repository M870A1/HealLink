package org.zerock.obj2026.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;       // For work_date
import java.sql.Time;       // For start_time, end_time
import java.sql.Timestamp;  // For created_at, updated_at (DATETIME in MySQL can map to Timestamp)
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
