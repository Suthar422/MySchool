package com.bookseat.authentication.controller;



import com.bookseat.authentication.config.UserPrincipal;
import com.bookseat.authentication.dto.StaffDto;
import com.bookseat.authentication.entity.Staff;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@Slf4j
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/staffs")
    public ResponseEntity<List<Staff>> getStaffs(Authentication authentication) {
        Users userPrincipal = (Users) authentication.getPrincipal();
        String schoolCode = userPrincipal.getSchoolCode();
        log.info("User Principal: {}", schoolCode);

        return new ResponseEntity<>(staffService.findAllBySchoolCode(schoolCode), HttpStatus.OK);
    }


    @PostMapping("createStaff")
    public ResponseEntity<Staff> createStaff(@RequestBody StaffDto staffDto, Authentication authentication) {
        Users userPrincipal = (Users) authentication.getPrincipal();
        log.info("User Principal: {}", userPrincipal);
        String schoolCode = userPrincipal.getSchoolCode();
        log.info("User Principal: {}", schoolCode);

        return new ResponseEntity<>(staffService.createStaff(staffDto, schoolCode), HttpStatus.CREATED);
    }


    @GetMapping("/staff")
    public Staff findStaffByName(@RequestParam String name) {
        return staffService.findStaffByName(name);
    }

    @DeleteMapping("/staff/{staffId}")
    public String deleteStudent(@PathVariable Long staffId) {
        staffService.deleteStaff(staffId);
        return "Staff deleted successfully";
    }


}
