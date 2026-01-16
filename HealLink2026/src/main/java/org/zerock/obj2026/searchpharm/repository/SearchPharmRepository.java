package org.zerock.obj2026.searchpharm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.zerock.obj2026.appointment.domain.Appointment;
import org.zerock.obj2026.hospital.domain.Hospital;

import java.util.List;
import java.util.Optional;

public interface SearchPharmRepository extends JpaRepository<Appointment, Long> {

    /**
     * [참고] 현재 DB 테이블명은 'modal_css'로 매핑되어 있으나,
     * 엔티티 구조 개선 시 'appointment'로 변경될 예정입니다.
     *
     * 유지보수: 질문자님이 말씀하신 대로 나중에 테이블 이름을 modal_css에서 appointments로 바꾸더라도,
     * 엔티티의 @Table(name = "...") 부분만 수정하면 리포지토리에 작성한 모든 쿼리를 수정할 필요가 없습니다.
     */

    @Query("SELECT h FROM Appointment a " +
            "JOIN a.schedule ds " +
            "JOIN ds.doctor d " +
            "JOIN d.hospital h " +
            "WHERE a.patient.patientId = :userId " +
            "AND a.status = 'RESERVED'")
    List<Hospital> findAllReservedHospitalsByUserId(@Param("userId") Long userId);
}
