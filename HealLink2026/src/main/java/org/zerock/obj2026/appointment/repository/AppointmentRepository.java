package org.zerock.obj2026.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.appointment.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
