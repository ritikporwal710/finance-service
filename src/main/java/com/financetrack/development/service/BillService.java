package com.financetrack.development.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financetrack.development.Repository.BillRepository;
import com.financetrack.development.model.Bill;

@Service
public class BillService {
    
    @Autowired
    private BillRepository billRepository;

    public Bill addBill(Bill data){
        try {
            System.out.println("data in service: " + data);
            Bill oldBill = billRepository.findByUser_IdAndBillName(data.getUser().getId(), data.getBillName());
            if(oldBill == null){
                oldBill = new Bill();
            }
            oldBill.setBillName(data.getBillName());
            oldBill.setAmount(data.getAmount());
            oldBill.setCategory(data.getCategory());
            oldBill.setDueDate(data.getDueDate());
            oldBill.setStatus(data.getStatus());
            oldBill.setUser(data.getUser());
            Bill savedBill = billRepository.save(oldBill);
            return savedBill;
        } catch (Exception e) {
            System.out.println("error in service: " + e);
            e.printStackTrace();
            return null;
        }
       
    }

    public List<Bill> getBill(UUID userId){
        try {
            System.out.println("userId in service: " + userId);
            List<Bill> bills = billRepository.findAllByUser_Id(userId);
            return bills;
        } catch (Exception e) {
            System.out.println("error in service: " + e);
            e.printStackTrace();
            return null;
        }
    }
}
