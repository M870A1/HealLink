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
    @Controller
    public class ReservationController {

        @GetMapping("/ucalendar")
        public String showCalendar() {
            return "son/ucalendar";
        }
    }

    @PostMapping
    public String createAppointment(@RequestParam("scheduleId") Long scheduleId,
                                    @RequestParam("patientId") Long patientId,
                                    @RequestParam("departmentId") Long departmentId,
                                    @RequestParam(value = "symptom", required = false) String symptom,
                                    @RequestParam(value = "note", required = false) String note) {
        log.info("POST /appointments - scheduleId: {}, patientId: {}", scheduleId, patientId);
        appointmentService.createAppointment(scheduleId, patientId, departmentId, symptom, note);
        return "redirect:/";
    }
}
