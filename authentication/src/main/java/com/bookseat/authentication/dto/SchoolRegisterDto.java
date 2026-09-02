package com.bookseat.authentication.dto;

import lombok.Data;

@Data
public class SchoolRegisterDto {
    private String schoolName;
    private String address;
    private String branch;

    private String email;
    private String password;
}

