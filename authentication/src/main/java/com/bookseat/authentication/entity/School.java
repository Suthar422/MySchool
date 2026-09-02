package com.bookseat.authentication.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "School")
@Data
public class School {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String schoolCode;
    private String schoolName;
    private String address;
    private String branch;

    //admin details
    private String email;
    private String password; // hashed
}

