package com.example.HospitalManagementSystem.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PatientRequestDTO {
    private long patientId;

    @NotBlank(message = "Patient Name is required ")
    private String patientName;

    @Min(value = 12,message = " patient age must be at least 12")
    @Max(value = 150,message = " patient age must be at most 150")
    private int patientAge;

    @NotBlank(message = "Disease Must Not Be Empty")
    private String patientDisease;

    @NotBlank(message = " Gender is required")
    private String gender;


    // Getter and setter methods implementations in DTOs only


    public long getPatientId() {
        return patientId;
    }

    public String getGender() {
        return gender;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public String getPatientDisease() {
        return patientDisease;
    }

    public String getPatientName() {
        return patientName;
    }


    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public void setPatientDisease(String patientDisease) {
        this.patientDisease = patientDisease;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
