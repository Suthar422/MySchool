package com.bookseat.authentication.util;


import com.bookseat.authentication.config.UserPrincipal;
import com.bookseat.authentication.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
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

        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            log.info("User Principal: {}", userPrincipal);
            return userPrincipal.getSchoolCode();
        }
        return null;
    }
}


