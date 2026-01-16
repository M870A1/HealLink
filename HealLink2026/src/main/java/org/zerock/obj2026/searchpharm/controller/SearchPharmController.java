package org.zerock.obj2026.searchpharm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.obj2026.member.dto.UserSecurityDTO;
import org.zerock.obj2026.searchpharm.service.SearchPharmService;

import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@PropertySource("classpath:auth.properties")
public class SearchPharmController {

    @Value("${kakao.js.key}")
    private String kakaoJsKey;

    private final SearchPharmService searchPharmService;

    @GetMapping("/searchpharm")
    public String pharmacyMainPage(
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO,
            Model model) {

        // 1. 카카오 JS 키 전달 (화면 렌더링에 필수)
        model.addAttribute("kakaoJsKey", kakaoJsKey);

        // 2. 로그인 여부 확인 및 예약 리스트 조회
        if (userSecurityDTO != null) {
            // UserSecurityDTO 내부에 ID를 반환하는 메서드가 getId()라고 가정합니다. (mno일 경우 수정 필요)
            Long loginUserId = userSecurityDTO.getUser().getUserId();

            // 서비스 호출 (수정한 대로 List<Map>을 반환함)
            List<Map<String, Object>> reservations = searchPharmService.getHospitalCoordinates(loginUserId);

            // 예약 데이터가 존재할 경우에만 모델에 담음
            if (reservations != null && !reservations.isEmpty()) {
                model.addAttribute("reservations", reservations);

                // 초기 화면 설정을 위해 첫 번째 예약 정보를 꺼내서 개별 변수로도 전달
                Map<String, Object> firstRes = reservations.get(0);
                model.addAttribute("lat", firstRes.get("lat"));
                model.addAttribute("lon", firstRes.get("lon"));
                model.addAttribute("hospitalName", firstRes.get("hospitalName"));

                log.info("로그인 사용자[{}] 예약 기반 접속: {}건 발견", loginUserId, reservations.size());
                return "searchpharm/main";
            }
        }

        // 3. 비로그인 사용자이거나 예약 내역이 없는 경우
        // JS에서 'GPS 모드'를 실행하도록 기존에 약속한 기본 위도값 전송
        model.addAttribute("lat", "35.1795543");
        log.info("비로그인 또는 예약 없음 - 일반 GPS 모드로 진입");

        return "searchpharm/main";
    }
}
