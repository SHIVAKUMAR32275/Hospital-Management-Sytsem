package com.example.HospitalManagementSystem.Exceptions;



public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException(String message){
        super(message);
    }
}
