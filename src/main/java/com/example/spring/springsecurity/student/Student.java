package com.example.spring.springsecurity.student;

public record Student(Integer studentId, String studentName) {

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
