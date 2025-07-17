package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.PatientRequestDTO;
import com.example.HospitalManagementSystem.Exceptions.PatientNotFoundException;
import com.example.HospitalManagementSystem.Models.Patient;
import com.example.HospitalManagementSystem.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImplementation implements PatientServices {

    @Autowired
    private PatientRepository patientRepository;


    @Override
    public Patient getSinglePatientDetails(Long id) {

        return patientRepository.findById(id)
                .orElseThrow(()->new PatientNotFoundException(" Patient with Id  "+id+" -->  doesn't Exist in Patient Table"));
    }



    @Override
    public Page<Patient> getAllPatients(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return patientRepository.findAll(pageable);
    }



    @Override
    public Patient createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setPatientId(patientRequestDTO.getPatientId());
        patient.setPatientAge(patientRequestDTO.getPatientAge());
        patient.setGender(patientRequestDTO.getGender());
        patient.setPatientName(patientRequestDTO.getPatientName());
        patient.setPatientDisease(patientRequestDTO.getPatientDisease());
        return patientRepository.save(patient);
    }



    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }




    @Override
    public Patient updatePatient(long id, PatientRequestDTO dto) {
        Patient existingPatient = patientRepository.findById(id).orElse(null);
        if (existingPatient == null) {
            return null;
        }
        existingPatient.setPatientName(dto.getPatientName());
        existingPatient.setPatientAge(dto.getPatientAge());
        existingPatient.setGender(dto.getGender());
        existingPatient.setPatientDisease(dto.getPatientDisease());
        return patientRepository.save(existingPatient);
    }



    @Override
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByPatientNameContainingIgnoreCase(name);
    }


}
