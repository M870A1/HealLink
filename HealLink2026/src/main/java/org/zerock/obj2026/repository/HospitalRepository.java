package org.zerock.obj2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.domain.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
