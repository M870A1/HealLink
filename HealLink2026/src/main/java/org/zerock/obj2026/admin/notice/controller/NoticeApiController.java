package org.zerock.obj2026.admin.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.zerock.obj2026.admin.notice.dto.NoticeAddRequest;
import org.zerock.obj2026.admin.notice.service.NoticeService;
import org.zerock.obj2026.member.dto.UserSecurityDTO;

// Axios
@RestController
@RequiredArgsConstructor
// Axios에서 호출하는 공통 경로 : /api/admin/notice
@RequestMapping("/api/admin/notice")
public class NoticeApiController {
    private final NoticeService noticeService;

    // 읽기는 없음

    // 쓰기
    @PostMapping

    public ResponseEntity<Long> createNotice(@RequestBody NoticeAddRequest dto,
                                             @AuthenticationPrincipal UserSecurityDTO userDetails) { // 로그인확인으로 UserSecurityDTO사용

        Long noticeId = noticeService.createNotice(dto, userDetails.getUsername());
        return ResponseEntity.ok(noticeId);
    }

    // 수정
    @PutMapping("/{id}")  //
        public ResponseEntity<Long> updateNotice(@PathVariable Long id, @RequestBody NoticeAddRequest dto,
                                             @AuthenticationPrincipal UserSecurityDTO userDetails) {

        noticeService.updateNotice(id, dto, userDetails.getUser().getUserId());
        return ResponseEntity.ok(id);
    }

    // 삭제
    @DeleteMapping("/{id}")
// @PreAuthorize는 필요 없습니다. SecurityConfig의 /admin/** 설정이 보호해줍니다.
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id,
                                             @AuthenticationPrincipal UserSecurityDTO userDetails) {

        // 1. 먼저 이 글이 존재하는지, 그리고 삭제할 권한(본인)이 있는지 확인해야 합니다.
        // 서비스에서 한 번에 처리하는 것이 가장 깔끔합니다.
        noticeService.deleteNotice(id, userDetails.getUser().getUserId());

        return ResponseEntity.ok().build();
    }

}
