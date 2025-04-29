package com.financetrack.development.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financetrack.development.Repository.TransactionRepository;
import com.financetrack.development.dto.ExpenseAndCategoryDTO;
import com.financetrack.development.dto.SalaryAndExpenseDTO;
import com.financetrack.development.dto.TransactionDTO;

@Service
public class DashboardService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionDTO> getAllExpensesByMonth(UUID userId) {
        List<TransactionDTO> expenses = transactionRepository.findAllByUser_IdWithQuery(userId);
        return expenses;
    }

    public List<SalaryAndExpenseDTO> getSalaryAndExpenses(UUID userId) {
        try {
            System.out.println("userId in service: " + userId);
            List<SalaryAndExpenseDTO> salaryAndExpenses = transactionRepository.findAllByUser_IdWithsalaryAndExpense(userId);
            System.out.println("salaryAndExpenses in service: " + salaryAndExpenses);
            return salaryAndExpenses;
        } catch (Exception e) {
            System.out.println("error in service: " + e);
            e.printStackTrace();
            return null;
        }
    }


    
    public List<ExpenseAndCategoryDTO> getExpenseAndCategory(UUID userId) {
        try {
            System.out.println("userId in service: " + userId);
            
            // Get current date
            LocalDate now = LocalDate.now();
            System.out.println("now: " + now); // ADD THIS
        
            String month = now.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH); 
            System.out.println("month: " + month); // ADD THIS
        
            int year = now.getYear(); 
            System.out.println("year: " + year); // ADD THIS



            System.out.println("this is working: " + month + " " + year );
            
            List<ExpenseAndCategoryDTO> expenseAndCategories = 
                transactionRepository.findAllByUser_IdWithCategoryAndExpense(userId, month, year);

                System.out.println("after fetching this is working: " + month + " " + year );
            
            System.out.println("expenseAndCategories in service: " + expenseAndCategories);
            return expenseAndCategories;
            
        } catch (Exception e) {
            System.out.println("error in service: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
}
