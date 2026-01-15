package org.zerock.obj2026.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.hospital.domain.Hospital;

public interface HospitalRepository
        extends JpaRepository<Hospital, String>, HospitalSearch {
}
