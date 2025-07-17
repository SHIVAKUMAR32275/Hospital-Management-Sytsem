package com.example.HospitalManagementSystem.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class DoctorRequestDTO {


    @NotBlank(message = "Doctor Name must not be Empty")
    private String doctorName;

    @NotBlank(message = "Doctor Specialisation required")
    private String specialist;

    @Min(value = 2,message = "at-least 2 years of Experience required ")
    @Max(value = 20,message = "at-most 20 years of Experience required ")
    private int doctorExperience;


    public String getDoctorName() {
        return doctorName;
    }

    public int getDoctorExperience() {
        return doctorExperience;
    }


    public String getSpecialist() {
        return specialist;
    }


    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorExperience(int doctorExperience) {
        this.doctorExperience = doctorExperience;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }
}
