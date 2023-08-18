package com.student.rest.api.mappers;

import com.common.security.api.mapper.BaseMapper;
import com.student.rest.api.entites.Student;
import com.student.service.api.dtos.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper extends BaseMapper<StudentDto,Student> {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
}
