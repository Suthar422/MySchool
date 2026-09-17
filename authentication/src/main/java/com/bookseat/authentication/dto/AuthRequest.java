package com.bookseat.authentication.dto;


import lombok.Data;

@Data
public class AuthRequest {

    //this is your email
    private String email;
    private String password;
    private String schoolCode;

}
