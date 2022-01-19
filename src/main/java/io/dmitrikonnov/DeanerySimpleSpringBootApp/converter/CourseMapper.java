package io.dmitrikonnov.DeanerySimpleSpringBootApp.converter;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseEntity;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {

    CourseMapper COURSE_MAPPER = Mappers.getMapper(CourseMapper.class);



    CourseDto entityToDto (CourseEntity courseEntity, @Context CycleAvoidingMappingContext context);
    CourseEntity dtoToEntity (CourseDto courseDto/*, @Context CycleAvoidingMappingContext context*/);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CourseEntity mergePersistedEntityAndDto (CourseDto courseDto,
                                            @MappingTarget CourseEntity courseEntity,
                                            @Context CycleAvoidingMappingContext context);

}
