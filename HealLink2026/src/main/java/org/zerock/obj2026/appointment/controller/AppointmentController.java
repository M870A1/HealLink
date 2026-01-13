package org.zerock.obj2026.appointment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.obj2026.appointment.service.AppointmentService;

@Controller
@RequestMapping("/appointments")
@Log4j2
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/new")
    public String showAppointmentForm() {
        log.info("Showing appointment form");
        return "appointment/form";
    }

    @PostMapping
    public String createAppointment(@RequestParam("scheduleId") Long scheduleId,
                                    @RequestParam("patientId") Long patientId) {
        log.info("POST /appointments - scheduleId: {}, patientId: {}", scheduleId, patientId);
        appointmentService.createAppointment(scheduleId, patientId);
        return "redirect:/"; // 예약 후 홈(병원 목록)으로 리다이렉트
    }
}
