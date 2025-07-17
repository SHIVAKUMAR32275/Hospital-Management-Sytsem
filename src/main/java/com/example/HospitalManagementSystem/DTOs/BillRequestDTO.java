package com.example.HospitalManagementSystem.DTOs;

import jakarta.validation.constraints.*;

public class BillRequestDTO {

    @Min(value = 1, message = "Patient ID must be at least 1")
    @Max(value = 1000, message = "Patient ID must be at most 1000")
    private long patientId;

    @DecimalMin(value = "3000.0", message = "Bill Amount must be at least 3000")
    @DecimalMax(value = "10000.0", message = "Bill Amount must be at most 10000")
    private double billAmount;

    @NotBlank(message = "Bill status must be 'Paid' or 'Pending'")
    private String billStatus;

    // Getters
    public long getPatientId() {
        return patientId;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public String getBillStatus() {
        return billStatus;
    }

    // Setters
    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }
}
