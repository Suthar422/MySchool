package com.bookseat.authentication.service.impl;


import com.bookseat.authentication.dto.StaffDto;
import com.bookseat.authentication.entity.Staff;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.repository.StaffRepository;
import com.bookseat.authentication.repository.UsersRepository;
import com.bookseat.authentication.service.StaffService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

//    @Value("${school.code}")
    private final String schoolCode = "ST-ST21";

    private final StaffRepository staffRepository;

    private final UsersRepository usersRepository;

    public StaffServiceImpl(StaffRepository staffRepository, UsersRepository usersRepository) {
        this.staffRepository = staffRepository;
        this.usersRepository = usersRepository;
    }

    //sorting
    Sort sort = Sort.by("name").ascending();


    @Override
    public List<Staff> findAllBySchoolCode(String schoolCode) {
        log.info("Finding all staff");
        return staffRepository.findBySchoolCode(sort, schoolCode);
    }

    @Override
    public Staff findStaffByName(String name) {
        log.info("Finding staff with name: {}", name);
        return staffRepository.findStaffByNameAndSchoolCode(name, schoolCode);
    }

    @Override
    public Staff createStaff(StaffDto staffDto, String schoolCode) {
        log.info("Saving staff details: {}", staffDto);
        Staff staff = new Staff();
        staff.setName(staffDto.getName());
        staff.setPhone(staffDto.getPhone());
        staff.setEmail(staffDto.getEmail());
        staff.setSchoolCode(schoolCode);


        log.info("Saving staff as user: {}", staffDto);
        Users users = new Users();
        users.setUsername(staffDto.getEmail());
        //currently using hard-coded password
        users.setPassword("Staff123");
        usersRepository.save(users);

        return staffRepository.save(staff);
    }

    @Override
    @Transactional
    public void deleteStaff(Long staffId) {
        log.info("Checking staff with id: {}", staffId);
        staffRepository.deleteById(staffId);
    }
}
