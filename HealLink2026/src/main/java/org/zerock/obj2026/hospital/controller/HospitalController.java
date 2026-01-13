package org.zerock.obj2026.hospital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.obj2026.hospital.dto.HPageRequestDTO;
import org.zerock.obj2026.hospital.dto.HospitalDTO;
import org.zerock.obj2026.hospital.dto.HpageResponseDTO;
import org.zerock.obj2026.hospital.service.HospitalService;

@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor
@Log4j2
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("/list")
    public String list(HPageRequestDTO pageRequestDTO, Model model) {
        log.info("GET /hospitals/list - {}", pageRequestDTO);
        HpageResponseDTO<HospitalDTO> responseDTO = hospitalService.list(pageRequestDTO);
        model.addAttribute("responseDTO", responseDTO);
        return "hospital/list";
    }

    @GetMapping("/{hospitalId}")
    public String detail(@PathVariable("hospitalId") String hospitalId, Model model) {
        log.info("GET /hospitals/{}", hospitalId);
        HospitalDTO hospitalDTO = hospitalService.getHospitalById(hospitalId);
        if (hospitalDTO == null) {
    // 얘기치 못한 에러 발생시
            return "redirect:/hospitals/list";
        }
        model.addAttribute("hospital", hospitalDTO);
        return "hospital/detail";
    }

    @GetMapping // hospitals 경로로 요청이 오면 /hospitals/list로 리다이렉트
    public String redirectToHospitalList() {
        return "redirect:/hospitals/list";
    }
}
