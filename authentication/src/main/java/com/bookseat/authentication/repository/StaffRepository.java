package com.bookseat.authentication.repository;

import com.bookseat.authentication.entity.Staff;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Staff findStaffByNameAndSchoolCode(String name, String schoolCode);

    List<Staff> findBySchoolCode(Sort sort, String schoolCode);
}
