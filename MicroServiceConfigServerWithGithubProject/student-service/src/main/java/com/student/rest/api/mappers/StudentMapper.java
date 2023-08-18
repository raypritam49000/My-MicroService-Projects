package com.student.rest.api.mappers;

import com.student.rest.api.dtos.StudentDto;
import com.student.rest.api.entites.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper extends BaseMapper<StudentDto,Student> {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
}
