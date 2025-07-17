package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.DoctorRequestDTO;
import com.example.HospitalManagementSystem.Models.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;


public interface DoctorServices {

    Page<Doctor> getListOfDoctors(int page, int size, String sortBy, String sortDir );

    Doctor getDoctorDetails(long id);

    Doctor createDoctor(DoctorRequestDTO dto);

    Doctor updateDoctor(long id, DoctorRequestDTO dto);

    String deleteDoctor(long id);

    List<Doctor> searchDoctorsByName(String name);

    List<Doctor> searchByDoctorSpecialisation(String spec);

}
