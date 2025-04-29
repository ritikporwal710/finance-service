package com.financetrack.development.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financetrack.development.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    Budget findByUser_Id(UUID userId);
}
