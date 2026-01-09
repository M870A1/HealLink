package org.zerock.obj2026.department.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class DepartmentDTO {
    private Long departmentId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
