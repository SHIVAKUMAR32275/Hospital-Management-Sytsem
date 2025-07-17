package com.example.HospitalManagementSystem.Repositories;

import com.example.HospitalManagementSystem.Models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {

    public List<Bill> findByBillStatusIgnoreCase(String status);
}
