package com.ashwetaw.dto.mapper;

import com.ashwetaw.dto.StudentDTO;
import com.ashwetaw.entities.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper extends BaseMapper<StudentDTO, Student>{
}
