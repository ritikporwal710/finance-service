package com.financetrack.development.model;

import java.util.UUID;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "otp_hash")
    private String otpHash;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "favourite_day")
    private String favouriteDay;

    @Column(name = "address")
    private String address;

    @Column(name = "is_subscribed", nullable = false)
    private boolean isSubscribed = false;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
