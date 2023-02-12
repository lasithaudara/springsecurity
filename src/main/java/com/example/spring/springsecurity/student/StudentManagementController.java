package com.example.spring.springsecurity.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v2/students")
public class StudentManagementController {

    List<Student> studentList = Arrays.asList(
            new Student(1, "lasitha"),
            new Student(2, "lakeendra"),
            new Student(3, "saman")
    );

    @GetMapping
    public List<Student> getAllStudents(){
        return studentList;
    }

    @PostMapping
    public void registerStudent(@RequestBody Student student){
        System.out.println("student details - " +student);
    }

    @PutMapping("{studentId}")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student){
        System.out.println("Student ID : "+studentId+ " - " + student);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable Integer studentId){
        System.out.println("Student ID - "+studentId+" - will be deleted");
    }

}
