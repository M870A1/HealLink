package org.zerock.obj2026.appointment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.obj2026.appointment.domain.Appointment;
import org.zerock.obj2026.appointment.repository.AppointmentRepository;
import org.zerock.obj2026.doctor_schedule.domain.DoctorSchedule;
import org.zerock.obj2026.doctor_schedule.repository.DoctorScheduleRepository;
import org.zerock.obj2026.patient.domain.Patient;
import org.zerock.obj2026.patient.repository.PatientRepository;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    @Override
    public Long createAppointment(Long scheduleId, Long patientId) {
        log.info("Creating appointment for schedule: {} and patient: {}", scheduleId, patientId);

        // 환자와 의사 스케줄 엔티티를 조회
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID: " + patientId));

        DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule ID: " + scheduleId));

        // 스케줄이 이미 예약되었는지 확인하는 로직 추가 자리

        // 예약 엔티티를 생성
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .schedule(schedule)
                // 상태는 기본값(RESERVED)을 사용
                .build();

        // 예약을 저장
        Appointment savedAppointment = appointmentRepository.save(appointment);

        log.info("Appointment created with ID: {}", savedAppointment.getAppointmentId());

        return savedAppointment.getAppointmentId();
    }
}
