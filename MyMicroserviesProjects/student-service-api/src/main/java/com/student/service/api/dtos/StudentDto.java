package com.student.service.api.dtos;

import com.course.service.api.dtos.CourseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private String studentId;
    private String name;
    private String email;
    private String city;
    private List<CourseDto> courseList;
}
