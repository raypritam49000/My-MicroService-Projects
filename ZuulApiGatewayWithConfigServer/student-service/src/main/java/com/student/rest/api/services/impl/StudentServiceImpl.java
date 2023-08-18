package com.student.rest.api.services.impl;

import com.student.rest.api.dtos.CourseDto;
import com.student.rest.api.dtos.StudentDto;
import com.student.rest.api.exceptions.ResourceNotFoundException;
import com.student.rest.api.external.service.CourseService;
import com.student.rest.api.mappers.StudentMapper;
import com.student.rest.api.repositories.StudentRepository;
import com.student.rest.api.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private RestTemplate restTemplate;
    private CourseService courseService;

    private WebClient webClient;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
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
    public List<StudentDto> getStudentsWebClient(){
        return StudentMapper.INSTANCE.toDtoList(studentRepository.findAll()).stream().peek(student -> {
            List<CourseDto> courseDtos = webClient.get().uri("http://course-service/courses/getAllCourse/"+student.getStudentId()).retrieve().bodyToFlux(CourseDto.class).collectList().block();
            student.setCourseList(courseDtos);
        }).toList();
    }

    @Override
    public StudentDto getStudentWebClientById(String studentId) {
        StudentDto studentDto = StudentMapper.INSTANCE.toDto(studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + studentId)));
        List<CourseDto> courseDtos = webClient.get().uri("http://course-service/courses/getAllCourse/"+studentDto.getStudentId()).retrieve().bodyToFlux(CourseDto.class).collectList().block();
        studentDto.setCourseList(courseDtos);
        return studentDto;
    }

    @Override
    public List<StudentDto> getStudentsFeignClient(){
        return StudentMapper.INSTANCE.toDtoList(studentRepository.findAll()).stream().peek(student -> {
            List<CourseDto> courseDtos = courseService.getAllCourseByStudentId(student.getStudentId());
            student.setCourseList(courseDtos);
        }).toList();
    }

    @Override
    public StudentDto getStudentFeignClientById(String studentId) {
        StudentDto studentDto = StudentMapper.INSTANCE.toDto(studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + studentId)));
        List<CourseDto> courseDtos = courseService.getAllCourseByStudentId(studentDto.getStudentId());
        studentDto.setCourseList(courseDtos);
        return studentDto;
    }

    @Override
    public List<StudentDto> getStudents() {
        return StudentMapper.INSTANCE.toDtoList(studentRepository.findAll()).stream().peek(student -> {
            ArrayList<CourseDto> courseDtos = restTemplate.getForObject("http://course-service/courses/getAllCourse/" + student.getStudentId(), ArrayList.class);
            student.setCourseList(courseDtos);
        }).toList();
    }

    @Override
    public StudentDto getStudentById(String studentId) {
        StudentDto studentDto = StudentMapper.INSTANCE.toDto(studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + studentId)));
        ArrayList<CourseDto> courseDtos = restTemplate.getForObject("http://course-service/courses/getAllCourse/" + studentDto.getStudentId(), ArrayList.class);
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
