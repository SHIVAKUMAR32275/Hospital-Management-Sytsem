package com.example.HospitalManagementSystem.Controllers;

import com.example.HospitalManagementSystem.DTOs.AppointmentRequestDTO;
import com.example.HospitalManagementSystem.Models.Appointment;
import com.example.HospitalManagementSystem.Services.AppointmentServices;
import org.springframework.data.domain.Page;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentServices appointmentServices;

    public AppointmentController(AppointmentServices appointmentServices) {
        this.appointmentServices = appointmentServices;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentServices.createAppointment(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Appointment>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Appointment> appointments = appointmentServices.getAllAppointments(page,size,sortBy,sortDir);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") Long id) {
        Appointment appointment = appointmentServices.getAppointmentById(id);
        if (appointment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") Long id,
                                                         @RequestBody AppointmentRequestDTO dto) {
        Appointment updated = appointmentServices.updateAppointment(id, dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") Long id) {
        appointmentServices.deleteAppointment(id);
        return ResponseEntity.ok("Appointment deleted successfully");
    }



    @GetMapping("/searchDoctor")
    public ResponseEntity<List<Appointment>> toSearchByDoctorName(String name){
        List<Appointment> appointments = appointmentServices.searchByDoctorName(name);

        return ResponseEntity.ok(appointments);
    }


    @GetMapping("/searchPatient")
    public ResponseEntity<List<Appointment>> toSearchByPatientName(String Name){
        List<Appointment> appointmentList = appointmentServices.searchByPatientName(Name);

        return ResponseEntity.ok(appointmentList);
    }


    @GetMapping("/searchStatus")
    public ResponseEntity<List<Appointment>> toSearchByStatus(String status){
        List<Appointment> appointments = appointmentServices.searchByAppointmentStatus(status);

        return ResponseEntity.ok(appointments);
    }
}
