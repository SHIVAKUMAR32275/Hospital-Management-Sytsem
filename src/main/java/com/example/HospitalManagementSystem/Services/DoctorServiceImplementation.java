package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.DoctorRequestDTO;
import com.example.HospitalManagementSystem.Models.Doctor;
import com.example.HospitalManagementSystem.Repositories.DoctorRepository;

//import org.springdoc.core.converters.models.Pageable;
//import org.springdoc.core.converters.models.Sort;

///  this is the right package to import the pageable and Sort
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;


@Service
public class DoctorServiceImplementation implements DoctorServices {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImplementation(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Page<Doctor> getListOfDoctors(int page,int size,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")?
                    Sort.by(sortBy).ascending():
                    Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);
        return  doctorRepository.findAll(pageable);
    }


    @Override
    public Doctor getDoctorDetails(long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Doctor createDoctor(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setDoctorName(dto.getDoctorName());
        doctor.setSpecialist(dto.getSpecialist());
        doctor.setExperience(dto.getDoctorExperience());
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(long id, DoctorRequestDTO dto) {
        Doctor updateDoctor = doctorRepository.findById(id).orElse(null);

        if (updateDoctor != null) {
            updateDoctor.setDoctorName(dto.getDoctorName());
            updateDoctor.setSpecialist(dto.getSpecialist());
            updateDoctor.setExperience(dto.getDoctorExperience());
            return doctorRepository.save(updateDoctor);
        }
        return null;
    }

    @Override
    public String deleteDoctor(long id) {
        doctorRepository.deleteById(id);
        return "Deleted successfully";
    }

    @Override
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepository.findByDoctorNameContainingIgnoreCase(name);
    }

    @Override
    public List<Doctor> searchByDoctorSpecialisation(String spec) {
        return doctorRepository.findBySpecialistIgnoreCase(spec);
    }
}
