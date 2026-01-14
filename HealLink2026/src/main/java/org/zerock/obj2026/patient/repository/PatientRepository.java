package org.zerock.obj2026.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.patient.domain.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
