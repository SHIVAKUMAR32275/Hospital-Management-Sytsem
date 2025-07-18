package com.example.HospitalManagementSystem.Repositories;

import com.example.HospitalManagementSystem.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findByPatientNameContainingIgnoreCase(String name);

}
