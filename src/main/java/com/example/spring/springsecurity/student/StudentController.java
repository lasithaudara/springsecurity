package com.example.spring.springsecurity.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    List<Student> studentList = Arrays.asList(
      new Student(1, "lasitha"),
      new Student(2, "lakeendra"),
      new Student(3, "saman")
    );

    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId ") Integer studentId){
        return studentList.parallelStream()
                .filter(student -> student.studentId().equals(studentId))
                .findFirst().orElseThrow(IllegalStateException::new);
    }
}
