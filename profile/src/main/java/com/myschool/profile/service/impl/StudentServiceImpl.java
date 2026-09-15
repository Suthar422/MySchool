package com.myschool.profile.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Value("${school.code}")
    private String schoolCode;

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //sorting
    Sort sort = Sort.by("name").ascending();
    @Override
    public List<Student> findAllStudentsDetails() {
        log.info("Finding all students");
        return studentRepository.findBySchoolCode(sort, schoolCode);
    }

    @Override
    public Student findStudentByName(String name) {
        log.info("Finding student with name: {}", name);
        return studentRepository.findStudentByNameAndSchoolCode(name, schoolCode);
    }

    @Override
    public Student saveStudent(StudentDto studentDto) {
        log.info("Saving student: {}", studentDto);
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setSchoolCode(schoolCode);
        student.setPhone(studentDto.getPhone());
        student.setStandard(studentDto.getStandard());
        student.setRollNumber(studentDto.getRollNumber());
        return studentRepository.save(student);
    }

//    @Override
//    public Student updateStudent(StudentDto studentDto) {
//        if(studentRepository.existsById(studentDto.getStd())) {
//        return studentRepository.save(student);
//        }
//    }

    @Override
    @Transactional
    public void deleteStudent(Long stdId) {
        log.info("Checking student with id: {}", stdId);
        if(studentRepository.existsByStdIdAndSchoolCode(stdId, schoolCode)) {
            log.info("Deleting student with id: {}", stdId);
            studentRepository.deleteByStdIdAndSchoolCode(stdId, schoolCode);
        }
        else {
            throw new RuntimeException("Student not found");
        }

     }
}
