package com.bookseat.authentication.dto;

import lombok.Data;

@Data
public class AuthRequest {
    //user request details
    private String email; //host name
    private String password;
    private String schoolCode;
  //  private String role;

}
