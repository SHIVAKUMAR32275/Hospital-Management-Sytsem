package com.example.HospitalManagementSystem.Controllers;

import com.example.HospitalManagementSystem.DTOs.BillRequestDTO;
import com.example.HospitalManagementSystem.Models.Bill;
import com.example.HospitalManagementSystem.Services.BillServices;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@PreAuthorize("hasRole('ACCOUNTANT')")
@RequestMapping("/api/bill")
public class BillController{

    private BillServices billServices;

    BillController(BillServices billServices){
        this.billServices = billServices;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillDetails(@PathVariable("id") long bill_id){
        Bill bill = billServices.getIndividualBillDetails(bill_id);

        if( bill == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bill);
    }


    @GetMapping
    public ResponseEntity<Page<Bill>> getListOfBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "billId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Page<Bill> bills = billServices.listOfBills(page,size,sortBy,sortDir);

        return ResponseEntity.ok(bills);
    }


    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody BillRequestDTO billRequestDTO){
        Bill createBill = billServices.generateBill(billRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createBill);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable("id") long id,@RequestBody BillRequestDTO billRequestDTO){
        Bill updated= billServices.updateBillDetails(id,billRequestDTO);

        if( updated == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public String deleteTheBill(@PathVariable("id") long bill_id){
        return billServices.deleteDetails(bill_id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Bill>> toGetStatusDetail(String status){
        List<Bill> bills = billServices.searchListOfBills(status);
        return ResponseEntity.ok(bills);
    }
}
