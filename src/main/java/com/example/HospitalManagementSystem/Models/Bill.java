package com.example.HospitalManagementSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billId;

    private double billAmount;
    private String billStatus;

    @OneToOne
    @JoinColumn(name = "patientId", nullable = false, unique = true)
    @JsonIgnoreProperties("bill")
    private Patient patient;

}
