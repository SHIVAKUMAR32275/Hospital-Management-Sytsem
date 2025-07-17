package com.example.HospitalManagementSystem.Advice;

import com.example.HospitalManagementSystem.DTOs.ErrorDTO;
import com.example.HospitalManagementSystem.Exceptions.PatientNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlingPatientNotFoundException(PatientNotFoundException ex){
        ErrorDTO e1 = new ErrorDTO();
        e1.setMessage(ex.getMessage());

        return new ResponseEntity<>(e1, HttpStatus.NOT_FOUND);
    }
}
