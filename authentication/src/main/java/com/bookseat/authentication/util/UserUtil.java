package com.bookseat.authentication.util;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserUtil {
    private String username;
    private String schoolCode;
}
