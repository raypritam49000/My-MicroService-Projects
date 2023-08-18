package com.student.service.api;

import com.student.service.api.dtos.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service")
public interface StudentServiceApi {

    @GetMapping("/students/byName/{name}")
    public ResponseEntity<StudentDto> getStudentByName(@PathVariable String name);

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getStudents();

    @GetMapping("/students/web-client/all")
    public ResponseEntity<List<StudentDto>> getAllStudentsWebClient();

    @GetMapping("/students/web-client/findById/{studentId}")
    public ResponseEntity<StudentDto> findStudentWebClientById(@PathVariable String studentId);

    @GetMapping("/students/all")
    public ResponseEntity<List<StudentDto>> getAllStudents();

    @GetMapping("/students/findById/{studentId}")
    public ResponseEntity<StudentDto> findStudentById(@PathVariable String studentId);

    @GetMapping("/students/id/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable String studentId);

    @PostMapping("/students")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto);

    @PutMapping("/students/{studentId}")
    public ResponseEntity<StudentDto> updateStudentById(@PathVariable String studentId, @RequestBody StudentDto studentDto);

    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentId);
}
