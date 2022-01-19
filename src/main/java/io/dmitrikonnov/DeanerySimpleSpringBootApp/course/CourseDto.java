package io.dmitrikonnov.DeanerySimpleSpringBootApp.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.BasicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.topic.TopicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.enums.LearningGroupType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@DTO
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel (description = "info about the course")

public class CourseDto extends BasicDto {



    @ApiModelProperty(notes = "name of course", required = true)

    @NotNull (message = "course name cannot be null")
    @JsonProperty(value = "name", required = true, defaultValue = "no name", access = JsonProperty.Access.READ_WRITE)
    private String name;

    @JsonProperty (value = "durationInUnities")
    private Integer durationInUnities;

    @JsonProperty (value = "teachingMaterial")
    private String teachingMaterial;

    @JsonProperty (value = "learningGroupType")
    private LearningGroupType learningGroupType;

    @JsonProperty (value = "topic")
    @JsonIgnoreProperties ("courses")
    private TopicDto topic;

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDurationInUnities() {
        return durationInUnities;
    }

    public void setDurationInUnities(Integer durationInUnities) {
        this.durationInUnities = durationInUnities;
    }

    public String getTeachingMaterial() {
        return teachingMaterial;
    }

    public void setTeachingMaterial(String teachingMaterial) {
        this.teachingMaterial = teachingMaterial;
    }
}