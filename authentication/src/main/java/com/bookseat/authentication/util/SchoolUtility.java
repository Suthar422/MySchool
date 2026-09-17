package com.bookseat.authentication.util;


import com.bookseat.authentication.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Slf4j
public class SchoolUtility {

    //extract school code from authentication jwt token
    public static String extractSchoolCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);
//        Users userPrincipal = (Users) authentication.getPrincipal();
//        String schoolCode = userPrincipal.getSchoolCode();
//        log.info("School Code: {}", schoolCode);
//        return schoolCode;

        if (authentication != null && authentication.getPrincipal() instanceof Users) {
            Users user = (Users) authentication.getPrincipal();
            return user.getSchoolCode();
        }
        return null;
    }
}


