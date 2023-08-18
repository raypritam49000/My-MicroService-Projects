package com.course.service.api;

import com.course.service.api.dtos.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "course-service")
public interface CourseServiceApi {

    @GetMapping("/courses/getAllCourse/{studentId}")
    public ResponseEntity<List<CourseDto>> getCoursesByStudentId(@PathVariable String studentId);

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable String courseId);

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDto>> getCourses();

    @PostMapping("/courses")
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto);

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable String courseId, @RequestBody CourseDto courseDto);

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseId);

    @GetMapping("/courses/getCourse/{studentId}")
    public ResponseEntity<CourseDto> getCourseByStudentId(@PathVariable String studentId);

}
