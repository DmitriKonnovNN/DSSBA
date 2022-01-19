package io.dmitrikonnov.DeanerySimpleSpringBootApp.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.course.CourseDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.BasicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@DTO
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TopicDto extends BasicDto {

    @JsonProperty
    private String name;

    @JsonProperty (value = "description")
    private String description;

    @JsonProperty
    @JsonIgnoreProperties ("topic")
    private List<CourseDto> courses;

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();

    public void addCourses (CourseDto courseDto) {
        if (courseDto == null) {
            courses = new ArrayList<>();
        }
        courses.add(courseDto);
    }

}
