package org.zerock.obj2026.appointment_detail.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.obj2026.appointment.domain.Appointment;
import org.zerock.obj2026.department.domain.Department;

import java.time.LocalDateTime;
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString(exclude = {"modal.css", "department"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment_detail")
public class AppointmentDetail {

    @Id
    @Column(name = "appointment_id")
    private Long appointmentId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Lob
    private String symptom;

    @Lob
    private String note;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
