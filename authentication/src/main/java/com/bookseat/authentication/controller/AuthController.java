package com.bookseat.authentication.controller;

import com.bookseat.authentication.config.UserPrincipal;
import com.bookseat.authentication.dto.AuthRequest;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.service.CustomUserDetailsService;
import com.bookseat.authentication.util.JwtUtil;
import com.bookseat.authentication.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authManager;

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;


    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

//authenticates the user and generates the token
    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("Authenticating User");
        //along with username and password, add school code to the authentication request

        UserUtil principal = UserUtil.builder()
                .username(authRequest.getEmail())
                .schoolCode(authRequest.getSchoolCode())
                .build();

        log.info("User Principal: {}", principal);

//        principal.setSchoolCode(authRequest.getSchoolCode());
//        principal.setUsername(authRequest.getEmail());

        log.info("Authenticating User with SchoolCode: {} Username: {} and Password: {}", authRequest.getSchoolCode(), authRequest.getEmail(), authRequest.getPassword());


        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        log.info("Authentication Successful and setting Security Context: {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("User Authenticated");
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
        log.info("User Details Loaded");
        log.info("Generating JWT Token");
        final String jwtToken = jwtUtil.generateToken(principal);
        log.info("JWT Token Generated  {}", jwtToken);
        return jwtToken;
    }


    @PostMapping("/register")
    public Users register(@RequestBody AuthRequest authRequest) {
        log.info("Registering User");
        return customUserDetailsService.saveUserDetails(authRequest);

    }

    @GetMapping("/fetch")
    public Users findUserByName(@RequestParam String username) {
        log.info("Fetching User");
        return customUserDetailsService.fetchUserByName(username);
    }

}
