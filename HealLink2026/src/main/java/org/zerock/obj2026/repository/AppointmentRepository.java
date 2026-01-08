package org.zerock.obj2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
