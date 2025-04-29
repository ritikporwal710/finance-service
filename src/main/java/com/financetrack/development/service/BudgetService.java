package com.financetrack.development.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financetrack.development.Repository.BudgetRepository;
import com.financetrack.development.model.Budget;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    
    public Budget updateBudget(Budget data){
        try {
            System.out.println("data in service: " + data);
            Budget oldBudget = budgetRepository.findByUser_Id(data.getUser().getId());
            System.out.println("oldBudget in service before: " + oldBudget);
            if(oldBudget == null){
                oldBudget = new Budget();
            }
            System.out.println("oldBudget in service after: " + oldBudget);
            oldBudget.setMonthlyIncome(data.getMonthlyIncome());
            oldBudget.setBankBalance(data.getBankBalance());
            oldBudget.setMonthlyBudget(data.getMonthlyBudget());
            oldBudget.setSavingsBudget(data.getSavingsBudget());
            oldBudget.setInvestments(data.getInvestments());
            oldBudget.setPfBalance(data.getPfBalance());
            oldBudget.setNetWorth(data.getNetWorth());
            oldBudget = data;
            oldBudget.setUser(data.getUser());

            System.out.println("oldBudget in service: " + oldBudget);
            // budgetRepository.save(null)
            return budgetRepository.save(oldBudget);
        } catch (Exception e) {
            System.out.println("error in budget service"+ e);
            e.printStackTrace();
            return null;
        }
      
    }

    public Budget getBudget(UUID userId){
        Budget budget = budgetRepository.findByUser_Id(userId);
        return budget;
    }
}
