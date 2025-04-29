package com.financetrack.development.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financetrack.development.Repository.SalaryRepository;
import com.financetrack.development.Repository.TransactionRepository;
import com.financetrack.development.dto.ExpenseAndCategoryDTO;
import com.financetrack.development.dto.NetWorthDTO;

@Service
public class NetWorthService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    public NetWorthDTO getNetWorth(UUID userId) {
        try {
            double totalIncome = salaryRepository.getTotalSalary(userId);
            double totalExpenses = transactionRepository.getTotalExpenses(userId, "expense");
            double totalSavings = totalIncome + totalExpenses;
            double netWorth = totalSavings;

            return new NetWorthDTO(totalIncome, netWorth, totalExpenses, totalSavings);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ExpenseAndCategoryDTO> getAllExpensesAndCategory(UUID userId) {
        try {
            List<ExpenseAndCategoryDTO> expenseAndCategories = transactionRepository.findAllByUser_IdWithCategoryAndExpenseForReport(userId);
            return expenseAndCategories;
        } catch (Exception e) {
       
            e.printStackTrace();
            return null;
        }
    }



}
