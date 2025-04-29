package com.financetrack.development.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.financetrack.development.dto.ExpenseAndCategoryDTO;
import com.financetrack.development.dto.SalaryAndExpenseDTO;
import com.financetrack.development.dto.TransactionDTO;
import com.financetrack.development.model.Transaction;

public interface TransactionRepository extends JpaRepository <Transaction, UUID> {
    List<Transaction> findAllByUser_Id(UUID userId);

    List<Transaction> findFirst5ByUser_IdOrderByCreatedAtDesc(UUID userId);

    @Query(value = "SELECT SUM(amount) FROM transactions WHERE user_id = :userId AND type = :type", nativeQuery = true)
    double getTotalExpenses(UUID userId, String type);
    
    @Query(value = "SELECT TO_CHAR(date, 'Month') AS month_name, EXTRACT(MONTH FROM date) AS month_number, EXTRACT(YEAR FROM date) AS year, SUM(amount) AS total_amount FROM transactions where user_id = :userId  GROUP BY month_name, month_number, year ORDER BY month_number", nativeQuery = true)
    List<TransactionDTO> findAllByUser_IdWithQuery(UUID userId);

    

    @Query(value = "SELECT TRIM(TO_CHAR(t.date, 'Month')) AS month_name, EXTRACT(MONTH FROM t.date) AS month_number, EXTRACT(YEAR FROM t.date) AS year, SUM(t.amount) AS total_amount, s.salary FROM transactions AS t LEFT JOIN salaries AS s ON s.user_id = t.user_id AND TRIM(TO_CHAR(t.date, 'Month')) = TRIM(s.month) WHERE t.user_id = :userId GROUP BY EXTRACT(MONTH FROM t.date), EXTRACT(YEAR FROM t.date), TRIM(TO_CHAR(t.date, 'Month')), s.salary ORDER BY month_number" ,nativeQuery = true)
List<SalaryAndExpenseDTO> findAllByUser_IdWithsalaryAndExpense(UUID userId);

    @Query(value = "SELECT category, SUM(amount) AS amount FROM transactions WHERE user_id = :userId AND trim(to_char(date, 'Month'))= :month AND EXTRACT(YEAR FROM date) = :year GROUP BY extract(month from date), extract(year from date), trim(to_char(date, 'Month')), category ORDER BY amount DESC", nativeQuery = true)
    List<ExpenseAndCategoryDTO> findAllByUser_IdWithCategoryAndExpense(UUID userId, String month, int year);

    @Query(value = "SELECT category, SUM(amount) AS amount FROM transactions WHERE user_id = :userId GROUP BY category ORDER BY amount DESC", nativeQuery = true)
    List<ExpenseAndCategoryDTO> findAllByUser_IdWithCategoryAndExpenseForReport(UUID userId);

}
