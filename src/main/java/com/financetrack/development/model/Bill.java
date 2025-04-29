package com.financetrack.development.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "bill_name", nullable = false)
    private String billName;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "due_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "status", nullable = false)
    private String status; // e.g., "paid", "unpaid", "overdue"

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

}
