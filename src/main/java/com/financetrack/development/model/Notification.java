package com.financetrack.development.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "due_days", nullable = false)
    private int dueDays;

    @Column(name = "status", nullable = false)
    private String status; // e.g., "active", "expired", etc.

    @Column(name = "total_limit", nullable = false)
    private double totalLimit;

    @Column(name = "type", nullable = false)
    private String type; // e.g., "reminder", "warning", etc.

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
