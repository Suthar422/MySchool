package com.bookseat.authentication.entity;


import com.bookseat.authentication.dto.ROLE;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class Users {

    @Id
    private String email;
    private String password;
    private String schoolCode;

    @Enumerated(EnumType.STRING)
    private ROLE role;

}
