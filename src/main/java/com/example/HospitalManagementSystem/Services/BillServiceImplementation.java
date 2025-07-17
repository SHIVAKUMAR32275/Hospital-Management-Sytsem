package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.BillRequestDTO;
import com.example.HospitalManagementSystem.Models.Bill;
import com.example.HospitalManagementSystem.Models.Patient;
import com.example.HospitalManagementSystem.Repositories.BillRepository;

import com.example.HospitalManagementSystem.Repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImplementation implements BillServices {

    private BillRepository billRepository;
    private final PatientRepository patientRepository;


    BillServiceImplementation(BillRepository billRepository,PatientRepository patientRepository){
        this.billRepository = billRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Bill getIndividualBillDetails(long bill_id) {

        return billRepository.findById(bill_id).orElse(null);
    }

    @Override
    public Page<Bill> listOfBills(int page,int size,String sortBy,String sortDir) {
        Sort sort = sortBy.equalsIgnoreCase("asc")?
                    Sort.by(sortBy).ascending():
                    Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);
        return billRepository.findAll(pageable);
    }

    @Override
    public Bill generateBill(BillRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(()->new RuntimeException(" Patient Not Found"));

        Bill bill = new Bill();
        bill.setBillAmount(dto.getBillAmount());
        bill.setBillStatus(dto.getBillStatus());
        bill.setPatient(patient);
        return billRepository.save(bill);
    }


    @Override
    public Bill updateBillDetails(long id, BillRequestDTO dto) {
        Bill updateBill = billRepository.findById(id).orElse(null);

        if (updateBill == null )
        {
            return null;
        }
        updateBill.setBillAmount(dto.getBillAmount());
        updateBill.setBillStatus(dto.getBillStatus());
        return billRepository.save(updateBill);
    }

    @Override
    public String deleteDetails(long bill_id) {
        billRepository.deleteById(bill_id);
        return "Bill deleted successfully";
    }

    @Override
    public List<Bill> searchListOfBills(String status) {
        return  billRepository.findByBillStatusIgnoreCase(status);
    }




}
