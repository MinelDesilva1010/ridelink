package com.ridelink.account.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String passwordHash; // In real life, use proper hashing
    private String fullName;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean active = true;
}
