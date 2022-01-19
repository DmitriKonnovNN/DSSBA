package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment.ExamDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.BasicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person.PersonDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import lombok.Data;

import java.util.Map;

@Data
@DTO
public class ResultsOfExamDto extends BasicDto {

   @JsonProperty (value = "currentExam")
    private ExamDto currentExam;

    @JsonProperty (value = "resultsMap")
    //@JsonBackReference
    private Map <String,SkillsToMarksDto> resultsMap;



}
