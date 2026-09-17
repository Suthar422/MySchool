package com.bookseat.authentication.service;



import com.bookseat.authentication.dto.StaffDto;
import com.bookseat.authentication.entity.Staff;

import java.util.List;

public interface StaffService {


    List<Staff> findAllBySchoolCode();

    Staff findStaffByName(String name);

    Staff createStaff(StaffDto staffDto, String schoolCode);

    //Student updateStudent(Student student);

    void deleteStaff(Long staffId);

}
