package com.financetrack.development.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseAndCategoryDTO {
    private String category;
    private double amount;
}
