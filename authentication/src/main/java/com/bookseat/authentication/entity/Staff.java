package com.bookseat.authentication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "staff")
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;
    private String schoolCode;
    private String name;
    private String phone;
}
