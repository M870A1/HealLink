package org.zerock.obj2026.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NoticeDTO {
    private Long noticeId;
    private String title;
    private String content;
    private Long writerId; // References UserDTO's userId
    private Integer viewCount;
    private Boolean isPinned; // TINYINT(1) can map to Boolean
    private Boolean isDeleted; // TINYINT(1) can map to Boolean
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
