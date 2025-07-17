package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.DTOs.BillRequestDTO;
import com.example.HospitalManagementSystem.Models.Bill;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillServices {

    public Bill getIndividualBillDetails(long bill_id);

    public Page<Bill> listOfBills(int page,int size,String sortBy,String sortDir);

    public Bill generateBill(BillRequestDTO dto);

    public Bill updateBillDetails(long id,BillRequestDTO dto);

    public String deleteDetails(long bill_id);

    public  List<Bill> searchListOfBills(String status);

}
