package org.zerock.obj2026.healthinfo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.obj2026.healthinfo.dto.HealthInfoResponse;
import org.zerock.obj2026.healthinfo.dto.PageRequestDTO;
import org.zerock.obj2026.healthinfo.service.HealthInfoService;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class HealthInfoViewController {
    private final HealthInfoService healthInfoService;
    
    // 목록
    @GetMapping({"/healthinfo"})
    // 페이지리퀘스트디티오, 모델
    public String getAllHealthInfo(PageRequestDTO pageRequestDTO, Model model){
        List<HealthInfoResponse> healthInfoList = healthInfoService.findAll();
        model.addAttribute("healthInfoList", healthInfoList);
        return "healthinfo";
    }

    // 읽기
    @GetMapping("/healthinfo/{id}")
    public String getHealthInfo(@PathVariable("id") long id,Model model){
        model.addAttribute("healthInfo", healthInfoService.findById(id));
        return "healthinfoview";
    }

    // 쓰기로 이동 axios
    @GetMapping("/healthinfo/create")
    public String createForm() {
        return "healthinfocreate"; // html 파일명
    }

    // 수정
    @GetMapping("/healthinfo/modify/{id}")
    public String modifyHealthInfo(@PathVariable Long id, Model model) {
        // 기존 데이터를 알아야 화면에 채워줄 수 있으므로 데이터를 조회해서 넘깁니다.
        HealthInfoResponse response = healthInfoService.findById(id);
        model.addAttribute("healthInfo", response);
        return "healthinfomodify"; // 만들으신 html 파일명
    }

}
