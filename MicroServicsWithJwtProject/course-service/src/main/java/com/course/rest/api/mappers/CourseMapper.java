package com.course.rest.api.mappers;

import com.common.security.api.mapper.BaseMapper;
import com.course.rest.api.dtos.CourseDto;
import com.course.rest.api.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper extends BaseMapper<CourseDto, Course> {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
}
