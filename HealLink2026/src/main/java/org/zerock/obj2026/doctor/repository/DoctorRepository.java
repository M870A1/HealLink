package org.zerock.obj2026.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.doctor.domain.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
