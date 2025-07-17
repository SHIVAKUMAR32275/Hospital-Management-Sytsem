package com.example.HospitalManagementSystem.DTOs;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequestDTO {

    @Min(value = 1,message = "patient id must be at-least 1")
    @Max(value = 1000,message = "patient id must be at-most 1000")
    private long patientId;


    @Min(value = 1,message = "The doctor id must be at least 1 ")
    @Max(value = 1000,message = "The doctor id must be at-most 1000")
    private long doctorId;

    @FutureOrPresent(message = "Appointment date must be today or in the future")
    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    @NotBlank(message = "Appointment status is required  (Scheduled,Completed)")
    private String appStatus;

    @NotBlank(message = "Purpose of Appointment is not Empty")
    private String purpose;

    // Getters and Setters

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }
}
