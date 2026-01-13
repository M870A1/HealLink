package org.zerock.obj2026.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NoticeDTO {
    private Long noticeId;
    private String title;
    private String content;
    private Long writerId; // UserDTO의 userId를 참조함
    private Integer viewCount;
    private Boolean isPinned; // TINYINT(1)  Boolean 으로 맵핑
    private Boolean isDeleted; // TINYINT(1)  Boolean 으로 맵핑
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
