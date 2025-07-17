package com.example.HospitalManagementSystem.Controllers;

import com.example.HospitalManagementSystem.DTOs.DoctorRequestDTO;
import com.example.HospitalManagementSystem.Models.Doctor;
import com.example.HospitalManagementSystem.Services.DoctorServices;
//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@PreAuthorize("hasRole('DOCTOR')")
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorServices doctorServices;

    // ✅ Constructor Injection (Recommended)
    public DoctorController(DoctorServices doctorServices) {
        this.doctorServices = doctorServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> toGetDoctorDetails(@PathVariable("id") long doctor_id) {
        Doctor doctor = doctorServices.getDoctorDetails(doctor_id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }

    @GetMapping
    public ResponseEntity<Page<Doctor>> toGetListOfDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Doctor> doctors = doctorServices.getListOfDoctors(page,size,sortBy,sortDir);
        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    public ResponseEntity<Doctor> toCreateDoctor(@RequestBody DoctorRequestDTO dto) {
        Doctor doctor = doctorServices.createDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> toUpdateDoctor(@PathVariable("id") long doctor_id,
                                                 @RequestBody DoctorRequestDTO doctorRequestDTO) {
        Doctor updateDoctor = doctorServices.updateDoctor(doctor_id, doctorRequestDTO);

        if (updateDoctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> toDeleteDoctor(@PathVariable("id") long id) {
        doctorServices.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> toGetDoctorsByName(String name){
        List<Doctor> doctors = doctorServices.searchDoctorsByName(name);
        return ResponseEntity.ok(doctors);
    }


    @GetMapping("/specialisation")
    public ResponseEntity<List<Doctor>> toGetDoctorNameBySpecialisation(String speciality){
        List<Doctor> doctors = doctorServices.searchByDoctorSpecialisation(speciality);

        return ResponseEntity.ok(doctors);
    }
}
