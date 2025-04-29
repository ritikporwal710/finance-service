package com.financetrack.development.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetWorthDTO {
    private double totalIncome;
    private double netWorth;
    private double totalExpenses;
    private double totalSavings;
}
