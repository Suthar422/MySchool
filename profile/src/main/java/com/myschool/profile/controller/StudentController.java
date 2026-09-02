package com.myschool.profile.controller;

import com.myschool.profile.dto.StudentDto;
import com.myschool.profile.entity.Student;
import com.myschool.profile.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.findAllStudentsDetails();
    }


    @PostMapping("student")
    public Student createStudent(@RequestBody StudentDto studentDto) {
        return studentService.saveStudent(studentDto);
    }


    @GetMapping("/student")
    public Student findStudentByName(@RequestParam String name) {
        return studentService.findStudentByName(name);
    }

    @DeleteMapping("/student/{stdId}")
    public String deleteStudent(@PathVariable Long stdId) {
        studentService.deleteStudent(stdId);
        return "Student deleted successfully";
    }
}
