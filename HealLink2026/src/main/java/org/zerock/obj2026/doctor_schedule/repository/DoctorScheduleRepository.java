package org.zerock.obj2026.doctor_schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.doctor_schedule.domain.DoctorSchedule;

import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    List<DoctorSchedule> findByDoctorDoctorId(Long doctorId);
}
