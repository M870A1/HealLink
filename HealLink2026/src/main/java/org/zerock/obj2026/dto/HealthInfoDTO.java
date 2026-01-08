package org.zerock.obj2026.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HealthInfoDTO {
    private Long healthInfoId;
    private String title;
    private String summary;
    private String content;
    private HealthInfoCategory category; // Using the enum
    private HealthInfoSourceType sourceType; // Using the enum
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
