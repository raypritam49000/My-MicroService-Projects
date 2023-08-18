package com.student.rest.api.services.impl;

import com.course.service.api.CourseServiceApi;
import com.course.service.api.dtos.CourseDto;
import com.student.rest.api.exceptions.ResourceNotFoundException;
import com.student.rest.api.mappers.StudentMapper;
import com.student.rest.api.repositories.StudentRepository;
import com.student.rest.api.services.StudentService;
import com.student.service.api.dtos.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private CourseServiceApi courseServiceApi;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setCourseServiceApi(CourseServiceApi courseServiceApi) {
        this.courseServiceApi = courseServiceApi;
    }

    @Override
    public StudentDto getStudentByName(String name) {
        return StudentMapper.INSTANCE.toDto(this.studentRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Student not found with name : " + name)));
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        return StudentMapper.INSTANCE.toDto(studentRepository.save(StudentMapper.INSTANCE.toEntity(studentDto)));
    }

    @Override
    public List<StudentDto> getStudents() {
        return StudentMapper.INSTANCE.toDtoList(studentRepository.findAll()).stream().peek(student -> {
            List<CourseDto> courseDtos = courseServiceApi.getCoursesByStudentId(student.getStudentId()).getBody();
            student.setCourseList(courseDtos);
        }).toList();
    }

    @Override
    public StudentDto getStudentById(String studentId) {
        StudentDto studentDto = StudentMapper.INSTANCE.toDto(studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + studentId)));
        List<CourseDto> courseDtos = courseServiceApi.getCoursesByStudentId(studentDto.getStudentId()).getBody();
        studentDto.setCourseList(courseDtos);
        return studentDto;
    }

    @Override
    public StudentDto updateStudentById(String studentId, StudentDto studentDto) {
        StudentDto existingStudent = getStudentById(studentId);
        existingStudent.setCity(studentDto.getCity());
        existingStudent.setEmail(studentDto.getEmail());
        existingStudent.setName(studentDto.getName());
        return StudentMapper.INSTANCE.toDto(studentRepository.save(StudentMapper.INSTANCE.toEntity(existingStudent)));
    }

    @Override
    public void deleteStudent(String studentId) {
        StudentDto existingStudent = getStudentById(studentId);
        this.studentRepository.delete(StudentMapper.INSTANCE.toEntity(existingStudent));
    }


}
