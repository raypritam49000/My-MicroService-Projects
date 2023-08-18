package com.student.rest.api.external.service;


import com.student.rest.api.dtos.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "course-service")
public interface CourseService {

    @GetMapping("/courses/getAllCourse/{studentId}")
    List<CourseDto> getAllCourseByStudentId(@PathVariable("studentId") String studentId);

}
