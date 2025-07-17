package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.AppointmentRequestDTO;
import com.example.HospitalManagementSystem.Models.Appointment;
import com.example.HospitalManagementSystem.Models.Doctor;
import com.example.HospitalManagementSystem.Models.Patient;
import com.example.HospitalManagementSystem.Repositories.AppointmentRepository;
import com.example.HospitalManagementSystem.Repositories.DoctorRepository;
import com.example.HospitalManagementSystem.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppointmentServiceImplementation implements AppointmentServices {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Appointment createAppointment(AppointmentRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElse(null);
        Doctor doctor = doctorRepository.findById(dto.getDoctorId()).orElse(null);

        if (patient == null || doctor == null) {
            return null; // or throw an exception
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setAppStatus(dto.getAppStatus());
        appointment.setPurpose(dto.getPurpose());

        return appointmentRepository.save(appointment);
    }

    @Override
    public Page<Appointment> getAllAppointments(int page,int size,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        return appointmentRepository.findAll(pageable);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public Appointment updateAppointment(Long id, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) return null;

        Patient patient = patientRepository.findById(dto.getPatientId()).orElse(null);
        Doctor doctor = doctorRepository.findById(dto.getDoctorId()).orElse(null);

        if (patient == null || doctor == null) return null;

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setAppStatus(dto.getAppStatus());
        appointment.setPurpose(dto.getPurpose());

        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> searchByDoctorName(String name) {
        return appointmentRepository.findByDoctorDoctorNameContainingIgnoreCase(name);
    }

    @Override
    public List<Appointment> searchByPatientName(String name) {
        return appointmentRepository.findByPatientPatientNameContainingIgnoreCase(name);
    }

    @Override
    public List<Appointment> searchByAppointmentStatus(String appStatus) {
        return appointmentRepository.findByAppStatusIgnoreCase(appStatus);
    }
}
