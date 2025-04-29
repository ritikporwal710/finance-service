package com.financetrack.development.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.financetrack.development.Repository.BillRepository;
import com.financetrack.development.Repository.UserRepository;
import com.financetrack.development.dto.ApiResponse;
import com.financetrack.development.model.Bill;
import com.financetrack.development.model.User;
import com.financetrack.development.service.BillService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class BillController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

    @PostMapping("/add-bill")
    public ResponseEntity<ApiResponse<String>> postMethodName(@RequestBody Bill data, HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));

        User user = userRepository.findById(userId).get();
        data.setUser(user);

        Bill bill = billService.addBill(data);

        if(bill == null){
            ApiResponse<String> billResponse = new ApiResponse<>(false, "Bill Not Added Successfully", null);
            return ResponseEntity.status(400).body(billResponse);
        }

        ApiResponse<String> billResponse = new ApiResponse<>(true, "Bill Added Successfully", null);
        return ResponseEntity.status(200).body(billResponse);
    }

    @GetMapping("/get-bills")
    public ResponseEntity<ApiResponse<List<Bill>>> getMethodName(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        List<Bill> bills = billService.getBill(userId);
    
        ApiResponse<List<Bill>> billData = new ApiResponse<>(true, "Bill Data Fetched Successfully", bills);
        return ResponseEntity.status(200).body(billData) ;
    }
    
    

}
