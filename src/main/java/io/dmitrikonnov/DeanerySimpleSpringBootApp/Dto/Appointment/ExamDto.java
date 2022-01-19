package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person.EmployeeDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.topic.TopicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import lombok.Data;

import java.util.List;


@Data
@DTO
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamDto extends AppointmentDto {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty (value = "subjectToExamination")
    private TopicDto subjectToExamination;

    @JsonProperty (value = "examType")
    private String examType;

    @JsonProperty (value = "examName")
    private String examName;

    @JsonProperty (value = "examiners")
    private List<EmployeeDto> examiners;

    @JsonProperty (value = "examinationSupervisors")
    private List <EmployeeDto> examinationSupervisors;

   @JsonProperty (value = "results")
    private ResultsOfExamDto results;

}
