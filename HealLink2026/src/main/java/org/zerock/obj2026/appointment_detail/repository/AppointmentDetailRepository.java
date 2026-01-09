package org.zerock.obj2026.appointment_detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.appointment_detail.domain.AppointmentDetail;

public interface AppointmentDetailRepository extends JpaRepository<AppointmentDetail, Long> {
}
