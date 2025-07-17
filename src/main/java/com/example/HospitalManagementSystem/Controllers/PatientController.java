package com.example.HospitalManagementSystem.Controllers;

import com.example.HospitalManagementSystem.DTOs.PatientRequestDTO;
import com.example.HospitalManagementSystem.Models.Patient;
import com.example.HospitalManagementSystem.Services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientServices patientServices;

    ///  Get Single Patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getSinglePatientDetails(@PathVariable("id") long id) {
        Patient patient = patientServices.getSinglePatientDetails(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        }
        return ResponseEntity.notFound().build();
    }

    ///  Get All Patients with Pagination and Sorting
    @GetMapping
    public ResponseEntity<Page<Patient>> getAllPatientDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "patient_id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Patient> patients = patientServices.getAllPatients(page, size, sortBy, sortDir);
        return ResponseEntity.ok(patients);
    }

    /// Create a New Patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = patientServices.createPatient(patientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }


    /// Delete Patient by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable long id) {
        patientServices.deletePatient(id);
        return ResponseEntity.ok("Patient deleted");
    }

    ///  Update Patient by ID
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable long id, @RequestBody PatientRequestDTO dto) {
        Patient existingPatient = patientServices.updatePatient(id, dto);
        if (existingPatient != null) {
            return ResponseEntity.ok(existingPatient);
        }
        return ResponseEntity.notFound().build();
    }

    ///  search by PatientName
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatientsByName(@RequestParam String name) {
        List<Patient> patients = patientServices.searchPatientsByName(name);
        return ResponseEntity.ok(patients);
    }

}
