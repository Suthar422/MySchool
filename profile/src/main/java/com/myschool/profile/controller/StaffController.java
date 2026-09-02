package com.myschool.profile.controller;


import com.myschool.profile.dto.StaffDto;
import com.myschool.profile.entity.Staff;
import com.myschool.profile.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/staffs")
    public List<Staff> getStaffs() {
        return staffService.findAllStaffDetails();
    }


    @PostMapping("staff")
    public Staff createStaff(@RequestBody StaffDto staffDto) {
        return staffService.saveStaff(staffDto);
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
