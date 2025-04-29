package com.financetrack.development.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "month", nullable = false)
    private String month; // e.g., "April"

    @Column(name = "year", nullable = false)
    private int year; // e.g., 2025

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

}
