package com.student.rest.api.controllers;

import com.student.rest.api.services.StudentService;
import com.student.service.api.dtos.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/byName/{name}")
    public ResponseEntity<StudentDto> getStudentByName(@PathVariable String name) {
        StudentDto student = studentService.getStudentByName(name);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudents() {
        List<StudentDto> students = studentService.getStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/id/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable String studentId) {
        StudentDto student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        StudentDto createdStudent = studentService.createStudent(studentDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudentById(@PathVariable String studentId, @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.updateStudentById(studentId, studentDto);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(Map.of("success", true, "statusCode", 200, "message", "Student has been deleted"),HttpStatus.OK);
    }


}
