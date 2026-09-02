package com.bookseat.authentication.service;

import com.bookseat.authentication.config.UserPrincipal;
import com.bookseat.authentication.dto.ROLE;
import com.bookseat.authentication.dto.SchoolRegisterDto;
import com.bookseat.authentication.entity.School;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.exception.AdminEmailAlreadyPresentException;
import com.bookseat.authentication.exception.SchoolAlreadyExistsException;
import com.bookseat.authentication.exception.SchoolNotFoundException;
import com.bookseat.authentication.repository.SchoolRepository;
import com.bookseat.authentication.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final SchoolRepository schoolRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(SchoolRepository schoolRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.schoolRepository = schoolRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserPrincipal loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with this email: " + email));
        log.info("User found with email: {}", email);
        log.info("User: {}", user);
        return new UserPrincipal(user);
    }


    //registering school
    public School saveSchoolDetails(SchoolRegisterDto schoolRequestDto) {
        log.info("Checking school details: {}", schoolRequestDto);
        if (schoolRepository.findByEmail(schoolRequestDto.getEmail()).isPresent()) {
            throw new SchoolAlreadyExistsException("School Admin already exists with email: " + schoolRequestDto.getEmail());
        }
        log.info("Registering School...");
        School school = new School();

        //create school code
//        String schoolCode = schoolRequestDto.getSchoolName().substring(0, 3).toUpperCase()
//                              +'-'+
//                schoolRequestDto.getEmail().substring(0, 3).toUpperCase();
//        log.info("School Code: {}", schoolCode);

        String schoolCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        log.info("School Code: {}", schoolCode);

        school.setEmail(schoolRequestDto.getEmail());
        school.setPassword(passwordEncoder.encode(schoolRequestDto.getPassword()));

        school.setAddress(schoolRequestDto.getAddress());
        school.setSchoolName(schoolRequestDto.getSchoolName());
        school.setBranch(schoolRequestDto.getBranch());

        school.setSchoolCode(schoolCode);
        log.info("Saving user details: {}", school);
        return schoolRepository.save(school);
    }

    //saving users details
    public Users saveUserDetails(SchoolRegisterDto schoolRequestDto, String schoolCode) {
        log.info("Checking school details: {}", schoolRequestDto);
        if (usersRepository.findByEmail(schoolRequestDto.getEmail()).isPresent()) {
            throw new AdminEmailAlreadyPresentException("School Admin already exists with email: " + schoolRequestDto.getEmail());
        }
        log.info("Saving Admin details inside common users tables...");
        Users users = new Users();
        users.setEmail(schoolRequestDto.getEmail());
        users.setPassword(passwordEncoder.encode(schoolRequestDto.getPassword()));
        users.setSchoolCode(schoolCode);
        users.setRole(ROLE.ADMIN);

        log.info("Saving user details: {}", users);
        return usersRepository.save(users);
    }


    public String findSchool(String schoolCode) {
        Optional<School> school = schoolRepository.findBySchoolCode(schoolCode);
       if(school.isEmpty() || school.get() == null) {
           throw new SchoolNotFoundException("School not found with code: " + schoolCode);
       }

        return school.get().getSchoolName();
    }

}

