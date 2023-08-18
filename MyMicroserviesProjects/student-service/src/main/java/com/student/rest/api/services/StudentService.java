package com.student.rest.api.services;

import com.student.service.api.dtos.StudentDto;

import java.util.List;

public interface StudentService {

    public StudentDto getStudentByName(String name);

    public StudentDto createStudent(StudentDto studentDto);

    public List<StudentDto> getStudents();

    public StudentDto getStudentById(String studentId);

    public StudentDto updateStudentById(String studentId, StudentDto studentDto);

    public void deleteStudent(String studentId);

}
