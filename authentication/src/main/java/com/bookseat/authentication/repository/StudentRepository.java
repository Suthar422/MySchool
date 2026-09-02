package com.bookseat.authentication.repository;


import com.bookseat.authentication.entity.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

   // List<Student> findAll(Sort sort, String s);

    Student findStudentByNameAndSchoolCode(String name, String s);

    List<Student> findBySchoolCode(Sort sort, String s);

    boolean existsByStdIdAndSchoolCode(Long stdId, String s);

    String deleteByStdIdAndSchoolCode(Long stdId, String s);
}
