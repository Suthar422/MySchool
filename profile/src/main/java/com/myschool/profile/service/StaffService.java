package com.myschool.profile.service;

import com.myschool.profile.dto.StaffDto;
import com.myschool.profile.dto.StudentDto;
import com.myschool.profile.entity.Staff;
import com.myschool.profile.entity.Student;

import java.util.List;

public interface StaffService {


    List<Staff> findAllStaffDetails();

    Staff findStaffByName(String name);

    Staff saveStaff(StaffDto staffDto);

    //Student updateStudent(Student student);

    void deleteStaff(Long staffId);

}
