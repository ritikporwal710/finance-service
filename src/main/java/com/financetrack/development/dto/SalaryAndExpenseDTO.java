package com.financetrack.development.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryAndExpenseDTO {
    
    private String monthName;
    private BigDecimal monthNumber;
    private BigDecimal year;
    private double totalAmount;
    private double salary;
}
