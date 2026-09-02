package com.myschool.profile.service.impl;

import com.myschool.profile.dto.StaffDto;
import com.myschool.profile.dto.StudentDto;
import com.myschool.profile.entity.Staff;
import com.myschool.profile.entity.Student;
import com.myschool.profile.repository.StaffRepository;
import com.myschool.profile.repository.StudentRepository;
import com.myschool.profile.service.StaffService;
import com.myschool.profile.service.StudentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    @Value("${school.code}")
    private String schoolCode;

    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    //sorting
    Sort sort = Sort.by("name").ascending();


    @Override
    public List<Staff> findAllStaffDetails() {
        log.info("Finding all staff");
        return staffRepository.findBySchoolCode(sort, schoolCode);
    }

    @Override
    public Staff findStaffByName(String name) {
        log.info("Finding staff with name: {}", name);
        return staffRepository.findStaffByNameAndSchoolCode(name, schoolCode);
    }

    @Override
    public Staff saveStaff(StaffDto staffDto) {
        log.info("Saving staff: {}", staffDto);
        Staff staff = new Staff();
        staff.setName(staffDto.getName());
        staff.setPhone(staffDto.getPhone());
        staff.setSchoolCode(schoolCode);
        return staffRepository.save(staff);
    }

    @Override
    @Transactional
    public void deleteStaff(Long staffId) {
        log.info("Checking staff with id: {}", staffId);
        staffRepository.deleteById(staffId);
    }
}
