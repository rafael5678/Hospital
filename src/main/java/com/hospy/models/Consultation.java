package com.hospy.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private LocalDateTime consultationDate;

    // Triage information
    private Double bloodPressureSystolic;
    private Double bloodPressureDiastolic;
    private Double weight;
    private Double temperature;
    
    @Column(columnDefinition = "TEXT")
    private String currentSymptoms;

    @Column(columnDefinition = "TEXT")
    private String physicalExamination;

    @Column(columnDefinition = "TEXT")
    private String diagnostics;

    @PrePersist
    protected void onCreate() {
        if (consultationDate == null) {
            consultationDate = LocalDateTime.now();
        }
    }
}
