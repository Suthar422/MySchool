package com.bookseat.authentication.service;


import com.bookseat.authentication.dto.StudentDto;
import com.bookseat.authentication.entity.Student;

import java.util.List;


public interface StudentService {

    List<Student> findAllStudentsDetails();

    Student findStudentByName(String name);

    Student saveStudent(StudentDto studentDto);

    //Student updateStudent(Student student);

    void deleteStudent(Long stdId);


}
