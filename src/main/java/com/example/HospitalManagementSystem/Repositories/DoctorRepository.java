package com.example.HospitalManagementSystem.Repositories;

import com.example.HospitalManagementSystem.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    List<Doctor> findByDoctorNameContainingIgnoreCase(String name);

    List<Doctor> findBySpecialistIgnoreCase(String name);
}
