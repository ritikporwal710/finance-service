package com.financetrack.development.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budget")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "monthly_income", nullable = false)
    private double monthlyIncome = 0.0;

    @Column(name = "bank_balance", nullable = false)
    private double bankBalance = 0.0;

    @Column(name = "monthly_budget", nullable = false)
    private double monthlyBudget = 0.0;

    @Column(name = "savings_budget", nullable = false)
    private double savingsBudget = 0.0;

    @Column(name = "investments", nullable = false)
    private double investments = 0.0;

    @Column(name = "pf_balance", nullable = false)
    private double pfBalance = 0.0;

    @Column(name = "net_worth", nullable = false)
    private double netWorth = 0.0;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

}
