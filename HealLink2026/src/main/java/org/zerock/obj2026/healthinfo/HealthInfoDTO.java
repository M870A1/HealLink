package org.zerock.obj2026.healthinfo;

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
    private HealthInfoCategory category; //Using the enum - 열거형의 멤버들을 현재 범위로 가져와서 이름 없이 직접 사용할 수 있게 해주는 기능
    private HealthInfoSourceType sourceType; //Using the enum - 열거형의 멤버들을 현재 범위로 가져와서 이름 없이 직접 사용할 수 있게 해주는 기능
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
