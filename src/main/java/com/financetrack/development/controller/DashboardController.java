package com.financetrack.development.controller;

import org.springframework.web.bind.annotation.RestController;

import com.financetrack.development.Repository.TransactionRepository;
import com.financetrack.development.dto.ApiResponse;
import com.financetrack.development.dto.ExpenseAndCategoryDTO;
import com.financetrack.development.dto.SalaryAndExpenseDTO;
import com.financetrack.development.dto.TransactionDTO;
import com.financetrack.development.service.DashboardService;
import com.financetrack.development.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping("/get-all-month-expenses")
    public ResponseEntity<ApiResponse<List<TransactionDTO>>> getExpenses(HttpServletRequest request) {
        UUID userId= UUID.fromString((String) request.getAttribute("userId"));
        List<TransactionDTO> expenses = dashboardService.getAllExpensesByMonth(userId); 
        ApiResponse<List<TransactionDTO>> entity = new ApiResponse<>(true, "Expense Fetched Successfully", expenses);
        return ResponseEntity.status(200).body(entity);
    }

    @GetMapping("get-salary-and-expenses")
    public ResponseEntity<ApiResponse<List<SalaryAndExpenseDTO>>> getSalaryAndExpenses(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));

        List<SalaryAndExpenseDTO> salaryAndExpenses = dashboardService.getSalaryAndExpenses(userId);

        ApiResponse<List<SalaryAndExpenseDTO>> entity = new ApiResponse<>(true, "Expense Fetched Successfully", salaryAndExpenses);
        return ResponseEntity.status(200).body(entity);
    }

    @GetMapping("/get-category-with-expense")
    public ResponseEntity<ApiResponse<List<ExpenseAndCategoryDTO>>> getMethodName(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));

        // System.out.println("month in controller: " + month + " year: " + year);

        List<ExpenseAndCategoryDTO> expenseAndCategories = dashboardService.getExpenseAndCategory(userId);
        ApiResponse<List<ExpenseAndCategoryDTO>> entity = new ApiResponse<>(true, "Expense Fetched Successfully", expenseAndCategories);
        return ResponseEntity.status(200).body(entity);
    }


    
    
    



    
}
