package com.myschool.profile.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stdId;
    private String schoolCode;
    private String name;
    private String phone;
    private String standard;
    private int rollNumber;
}
