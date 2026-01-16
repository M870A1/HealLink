package org.zerock.obj2026.searchpharm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.obj2026.hospital.domain.Hospital;
import org.zerock.obj2026.searchpharm.repository.SearchPharmRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SearchPharmService {
    private final SearchPharmRepository searchPharmRepository;

    public List<Map<String, Object>> getHospitalCoordinates(Long userId){
        if (userId == null) {
            log.info("사용자 ID가 없어 빈 목록을 반환합니다.");
            return java.util.Collections.emptyList();
        }

        List<Hospital> hospitalList = searchPharmRepository.findAllReservedHospitalsByUserId(userId);
        // 1. 로그인 했고 + RESERVED 상태인 병원이 있는지 조회

        return hospitalList.stream()
                .map(hospital -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("lat", hospital.getWgs84Lat().doubleValue());
                    map.put("lon", hospital.getWgs84Lon().doubleValue());
                    map.put("hospitalName", hospital.getDutyName());
                    return map;
                })
                .collect(Collectors.toList());

    }
}


