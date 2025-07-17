package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.AppointmentRequestDTO;
import com.example.HospitalManagementSystem.Models.Appointment;
import org.springframework.data.domain.Page;


import java.util.List;

public interface AppointmentServices {

    Appointment createAppointment(AppointmentRequestDTO dto);

    Page<Appointment> getAllAppointments(int page,int size,String sortBy,String sortDir);

    Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Long id, AppointmentRequestDTO dto);

    void deleteAppointment(Long id);

    List<Appointment> searchByDoctorName(String name);

    List<Appointment> searchByPatientName(String name);

    List<Appointment> searchByAppointmentStatus(String appStatus);
}
