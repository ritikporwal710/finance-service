package com.financetrack.development.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.financetrack.development.Repository.TransactionRepository;
import com.financetrack.development.model.Transaction;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllExpenses(UUID userId) {
        System.out.println("userId in service: " + userId);
        return transactionRepository.findAllByUser_Id(userId);
    }

    public Transaction addExpense(Transaction data){
        System.out.println("data in service: " + data);
        Transaction transaction = new Transaction();
        transaction.setAmount(data.getAmount());
        transaction.setCategory(data.getCategory());
        transaction.setDescription(data.getDescription());
        transaction.setDate(data.getDate());
        transaction.setUser(data.getUser());
        transaction.setType(data.getType());
        
        Transaction newTransaction = transactionRepository.save(transaction);
        return newTransaction;
    }

    public List<Transaction> getRecentTransactions(UUID userId) {
        return transactionRepository.findFirst5ByUser_IdOrderByCreatedAtDesc(userId);
    }
    







}
