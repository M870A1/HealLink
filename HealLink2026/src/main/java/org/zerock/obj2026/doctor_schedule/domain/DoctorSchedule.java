package org.zerock.obj2026.doctor_schedule.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.obj2026.doctor.domain.Doctor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString(exclude = "doctor")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_schedule", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"doctor_id", "work_date", "start_time", "end_time"})
})
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private Date workDate;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAvailable = true;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
