package org.zerock.obj2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.domain.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
}
