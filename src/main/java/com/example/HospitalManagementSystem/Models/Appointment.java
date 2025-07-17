package com.example.HospitalManagementSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appointmentId;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appStatus;
    private String purpose;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", nullable = false)
    private Patient patient;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId", nullable = false)
    private Doctor doctor;
}
