package com.financetrack.development.controller;

import org.springframework.web.bind.annotation.RestController;

import com.financetrack.development.dto.ApiResponse;
import com.financetrack.development.dto.ExpenseAndCategoryDTO;
import com.financetrack.development.dto.NetWorthDTO;
import com.financetrack.development.service.NetWorthService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class NetWorthController {

    @Autowired
    private NetWorthService netWorthService;
    
    @GetMapping("/get-report")
    public ResponseEntity<ApiResponse<NetWorthDTO>> getReport(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        NetWorthDTO netWorthDTO = netWorthService.getNetWorth(userId);
        ApiResponse<NetWorthDTO> entity = new ApiResponse<>(true, "Net Worth Fetched Successfully", netWorthDTO);
        return ResponseEntity.status(200).body(entity);
    }

    @GetMapping("/category-expense-wise")
    public ResponseEntity<ApiResponse<List<ExpenseAndCategoryDTO>>> getCategoryWise(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));

        List<ExpenseAndCategoryDTO> expenseAndCategories = netWorthService.getAllExpensesAndCategory(userId);
        ApiResponse<List<ExpenseAndCategoryDTO>> entity = new ApiResponse<>(true, "Expense Fetched Successfully", expenseAndCategories);

        return ResponseEntity.status(200).body(entity);
    }
    
    
}
