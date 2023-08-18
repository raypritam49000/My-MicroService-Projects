package com.course.rest.api.services;


import com.course.service.api.dtos.CourseDto;

import java.util.List;

public interface CourseService {
    public CourseDto getCourseByStudentId(String studentId);
    public CourseDto getCourseById(String courseId);
    public List<CourseDto> getCourses();
    public CourseDto addCourse(CourseDto courseDto);
    public CourseDto updateCourse(String courseId,CourseDto courseDto);
    public void deleteCourse(String courseId);
    public List<CourseDto> findAllByStudentId(String studentId);
}
