package com.myschool.profile.service;

import com.myschool.profile.dto.StudentDto;
import com.myschool.profile.entity.Student;

import java.util.List;


public interface StudentService {

    List<Student> findAllStudentsDetails();

    Student findStudentByName(String name);

    Student saveStudent(StudentDto studentDto);

    //Student updateStudent(Student student);

    void deleteStudent(Long stdId);


}
