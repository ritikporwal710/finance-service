package com.financetrack.development.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.financetrack.development.model.Salary;

public interface SalaryRepository extends JpaRepository<Salary, UUID>{
    Salary findByUser_IdAndMonth(UUID userId, String month);

    List<Salary> findAllByUser_Id(UUID userId);

    @Query(value="SELECT SUM(salary) FROM salaries WHERE user_id = :userId", nativeQuery = true)
    double getTotalSalary(UUID userId);
}
