package com.bookseat.authentication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDto {
    private String name;
    private String phone;
    private String standard;
    private int rollNumber;
}
