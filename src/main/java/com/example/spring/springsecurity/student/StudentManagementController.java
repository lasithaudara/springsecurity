package com.example.spring.springsecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    List<Student> studentList = Arrays.asList(
            new Student(1, "lasitha"),
            new Student(2, "lakeendra"),
            new Student(3, "saman") );

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINTRAINEE')")
    public List<Student> getAllStudents(){
        return studentList;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('course:write')")
    public void registerStudent(@RequestBody Student student){
        System.out.println("student details - " +student);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('course:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student){
        System.out.println("Student ID : "+studentId+ " - " + student);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('course:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("Student ID - "+studentId+" - will be deleted");
    }
}
