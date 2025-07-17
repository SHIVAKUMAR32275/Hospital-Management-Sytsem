package com.example.HospitalManagementSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;

    private int patientAge;
    private String patientName;
    private String Gender;
    private String patientDisease;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private Bill bill;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
