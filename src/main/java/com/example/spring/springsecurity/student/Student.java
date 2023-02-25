package com.example.spring.springsecurity.student;

import jakarta.persistence.*;

@Entity
public record Student(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Integer studentId,
        @Column(name = "student_name")
        String studentName) {

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
