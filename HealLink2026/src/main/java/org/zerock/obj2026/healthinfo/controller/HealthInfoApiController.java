package org.zerock.obj2026.healthinfo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.obj2026.healthinfo.dto.HealthInfoAddRequest;
import org.zerock.obj2026.healthinfo.dto.HealthInfoResponse;
import org.zerock.obj2026.healthinfo.service.HealthInfoService;

import java.util.List;

// body(healthinfos) -> json,  body(savedId + "번 등록 성공") -> 문자열,
@RestController
@RequiredArgsConstructor

public class HealthInfoApiController
{
    private final HealthInfoService healthInfoService;

    // 목록
    @GetMapping("/api/healthinfo")
    public ResponseEntity<List<HealthInfoResponse>> findAllHealthInfo() {
        // 헬스인포리스폰스, 헬스인포서비스.파인드올
        List<HealthInfoResponse> healthinfos = healthInfoService.findAll();
        // 리스폰스엔티티 : 정해진 이름
        return ResponseEntity.ok().body(healthinfos);
    }

    // 쓰기 axios
    @PostMapping("/api/healthinfo")
    public ResponseEntity<?> create(@Valid @RequestBody HealthInfoAddRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력값이 올바르지 않습니다.");
        }
        Long savedId = healthInfoService.createPost(dto);
        return ResponseEntity.ok().body(savedId + "번 등록 성공");
    }

    // 삭제 axios
    @DeleteMapping("/api/healthinfo/{id}")
    public ResponseEntity<Void> deleteHealthInfo(@PathVariable Long id) {
        healthInfoService.delete(id); // 서비스에서 deleteById 실행
        return ResponseEntity.ok().build();
    }
    // 수정 axios
    @PutMapping("/api/healthinfo/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody HealthInfoAddRequest dto) {
        Long updatedId = healthInfoService.update(id, dto);
        return ResponseEntity.ok().body(updatedId);
    }



}
