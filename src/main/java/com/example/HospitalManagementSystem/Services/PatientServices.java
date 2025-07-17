package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.PatientRequestDTO;
import com.example.HospitalManagementSystem.Models.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientServices {

    Patient getSinglePatientDetails(Long id);

    Page<Patient> getAllPatients(int page, int size, String sortBy, String sortDir);

    Patient createPatient(PatientRequestDTO patientRequestDTO);

    void deletePatient(Long id);

    Patient updatePatient(long id, PatientRequestDTO dto);

    List<Patient> searchPatientsByName(String name);

}
