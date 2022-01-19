package io.dmitrikonnov.DeanerySimpleSpringBootApp.converter;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.topic.TopicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.topic.TopicEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopicMapper {
    TopicMapper TOPIC_MAPPER = Mappers.getMapper(TopicMapper.class);

    TopicDto entityToDto(TopicEntity topicEntity, @Context CycleAvoidingMappingContext context);

    TopicEntity dtoToEntity(TopicDto topicDto/*, @Context CycleAvoidingMappingContext context*/);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TopicEntity mergePersistedEntityAndDto(TopicDto topicDto,
                                           @MappingTarget TopicEntity topicEntity,
                                           @Context CycleAvoidingMappingContext context);

    //TODO: TRY OUT:
    // i) constants @Mapping (target = "field name", constant="some value") for primitives;
    // use @Mapper (componentModel = "spring", imports = {SomeCLass.class}) for initialize constant non-primitives
    // ii) JavaExpressions @Mapping (target = "field name", expression = "java(Period(LocalDate.now(), getSomeOtherDate())"),
    // use @Mapper (-//-, imports = {Period.class, LocalDate.class})
    // iii) Default Methods of Interfaces: default returnType doSomeLogic (args) {....return someObject;}. ...(expression = "java(doSomeLogic(args))")
    // iv)dependsOn to set the required order of ??? generated mapping code ???
    // v)@Mapping (target = "some field", ignore = true) in order to not map concerned field.
    // vi) Try to use without context.


}