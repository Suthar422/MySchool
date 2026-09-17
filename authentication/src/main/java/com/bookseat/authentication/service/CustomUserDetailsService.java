package com.bookseat.authentication.service;

import com.bookseat.authentication.config.UserPrincipal;
import com.bookseat.authentication.dto.AuthRequest;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserPrincipal loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", email);
        Users user  = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        log.info("User found: {}", user);
        return new UserPrincipal(user);
    }



    //save user details
    public Users saveUserDetails(AuthRequest authRequest) {
        Users user = new Users();
        user.setEmail(authRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        log.info("Saving user details: {}", user);
        return usersRepository.save(user);
    }

    public Users fetchUserByName(String username) {
        log.info("Fetching user by name: {}", username);
        return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
