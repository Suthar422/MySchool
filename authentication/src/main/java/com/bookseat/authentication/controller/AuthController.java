package com.bookseat.authentication.controller;

import com.bookseat.authentication.config.UserPrincipal;
import com.bookseat.authentication.dto.AuthRequest;
import com.bookseat.authentication.dto.SchoolRegisterDto;
import com.bookseat.authentication.entity.School;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.service.CustomUserDetailsService;
import com.bookseat.authentication.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//authenticates the user and generates the token
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("Authenticating School admin details");


        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        log.info("Authentication Successful and setting Security Context {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Admin Authenticated");
        final UserPrincipal userPrincipal = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
     //   final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userPrincipal.getRole();

        log.info("Role: {}", role);

        log.info("User Details Loaded");
        log.info("Generating JWT Token");

        final String jwtToken = jwtUtil.generateToken(userPrincipal.getUsername(), authRequest.getSchoolCode(), role);
        log.info("JWT Token Generated  {}", jwtToken);

        return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
    }



    @PostMapping("/registerSchool")
    public ResponseEntity<?> register(@RequestBody SchoolRegisterDto  schoolRequestDto) {
        log.info("Registering a new School");
        School school = customUserDetailsService.saveSchoolDetails(schoolRequestDto);

        //saving admin details inside common users
        log.info("Saving admin details");
        Users users = customUserDetailsService.saveUserDetails(schoolRequestDto, school.getSchoolCode());

        // 🌟 This Map format directly resolves the frontend's undefined property issue
        Map<String, String> responsePayload = Map.of("schoolCode", school.getSchoolCode());
        return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);

    }

    @GetMapping("/findSchool")
    public ResponseEntity<Map<String, String>> findSchool(@RequestParam String schoolCode) {
        String schoolName = customUserDetailsService.findSchool(schoolCode);

        if (schoolName != null && !schoolName.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("schoolName", schoolName);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("message", "School code not recognized.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }




}
