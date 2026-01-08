package org.zerock.obj2026.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.obj2026.member.domain.User;

import java.sql.Date;
import java.time.LocalDateTime;
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString(exclude = "user") // Exclude user from toString to avoid recursion
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @Column(name = "patient_id")
    private Long patientId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "patient_id")
    private User user;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 3)
    private String bloodType;

    @Column(nullable = false)
    private Boolean hasAllergies = false;

    @Lob // For TEXT type
    private String allergies;

    @Lob // For TEXT type
    private String medicalHistory;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
