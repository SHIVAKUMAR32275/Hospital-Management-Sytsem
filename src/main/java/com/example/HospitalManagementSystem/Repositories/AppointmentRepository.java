package com.example.HospitalManagementSystem.Repositories;

import com.example.HospitalManagementSystem.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorDoctorNameContainingIgnoreCase(String doctorName);

    List<Appointment> findByPatientPatientNameContainingIgnoreCase(String patientName);

    List<Appointment> findByAppStatusIgnoreCase(String appStatus);

}
