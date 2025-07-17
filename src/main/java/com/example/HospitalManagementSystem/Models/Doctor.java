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
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorId;

    private String doctorName;
    private String specialist;
    private int experience;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Patient> patientList;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
