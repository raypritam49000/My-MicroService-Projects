package com.course.rest.api.services.impl;

import com.course.rest.api.exceptions.ResourceNotFoundException;
import com.course.rest.api.mappers.CourseMapper;
import com.course.rest.api.repositories.CourseRepository;
import com.course.rest.api.services.CourseService;
import com.course.service.api.dtos.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseDto getCourseByStudentId(String studentId) {
        return CourseMapper.INSTANCE.toDto(courseRepository.findByStudentId(studentId).orElseThrow(()-> new ResourceNotFoundException("Student not found with studentId : "+studentId)));
    }

    @Override
    public CourseDto getCourseById(String courseId) {
        return CourseMapper.INSTANCE.toDto(courseRepository.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Student not found with courseId : "+courseId)));
    }

    @Override
    public List<CourseDto> getCourses() {
        return CourseMapper.INSTANCE.toDtoList(courseRepository.findAll());
    }

    @Override
    public CourseDto addCourse(CourseDto courseDto) {
        return CourseMapper.INSTANCE.toDto(courseRepository.save(CourseMapper.INSTANCE.toEntity(courseDto)));
    }

    @Override
    public CourseDto updateCourse(String courseId, CourseDto courseDto) {
        CourseDto existingCourse = getCourseById(courseId);
        existingCourse.setDuration(courseDto.getDuration());
        existingCourse.setName(courseDto.getName());
        existingCourse.setPrice(courseDto.getPrice());
        existingCourse.setStudentId(courseDto.getStudentId());
        return CourseMapper.INSTANCE.toDto(courseRepository.save(CourseMapper.INSTANCE.toEntity(existingCourse)));
    }

    @Override
    public void deleteCourse(String courseId) {
        CourseDto existingCourse = getCourseById(courseId);
        courseRepository.delete(CourseMapper.INSTANCE.toEntity(existingCourse));
    }

    @Override
    public List<CourseDto> findAllByStudentId(String studentId) {
        return CourseMapper.INSTANCE.toDtoList(courseRepository.findAllByStudentId(studentId));
    }
}
