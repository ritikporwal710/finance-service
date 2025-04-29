package com.financetrack.development.controller;

import org.springframework.web.bind.annotation.RestController;

import com.financetrack.development.Repository.UserRepository;
import com.financetrack.development.dto.ApiResponse;
import com.financetrack.development.model.Budget;
import com.financetrack.development.model.User;
import com.financetrack.development.service.BudgetService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class BudgetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetService budgetService;     

    @PostMapping("/update-budget")
    public ResponseEntity<ApiResponse<String>> postMethodName(@RequestBody Budget data, HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        User user = userRepository.findById(userId).get();
        data.setUser(user);
        System.out.println("data coming in controller"+ data);
        Budget budget = budgetService.updateBudget(data);
        if(budget == null){
            ApiResponse<String> entity = new ApiResponse<>(false, "Budget Not Added Successfully", null);
            return ResponseEntity.status(400).body(entity);
        }
        ApiResponse<String> entity = new ApiResponse<>(true, "Budget Added Successfully", null);
        
        return ResponseEntity.status(200).body(entity);
    }

    @GetMapping("/get-budget")
    public ResponseEntity<ApiResponse<Budget>> getMethodName(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        Budget budget = budgetService.getBudget(userId);
        ApiResponse<Budget> entity = new ApiResponse<>(true, "Budget Fetched Successfully", budget);
        return ResponseEntity.status(200).body(entity);
    }
    
    
}
