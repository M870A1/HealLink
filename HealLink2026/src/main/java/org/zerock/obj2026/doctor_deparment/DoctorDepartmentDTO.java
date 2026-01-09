package org.zerock.obj2026.doctor_deparment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DoctorDepartmentDTO {
    private Long doctorId;
    private Long departmentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
