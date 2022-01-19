package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.BasicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person.PersonDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public abstract class AppointmentDto implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @JsonProperty(value = "date")
    private Date date;

   /* @JsonProperty(value = "group")
    private GroupDto group;*/

    @JsonProperty(value = "comment")
    private String comment;

    @JsonProperty (value = "employees")
    private List<PersonDto> CurrentEmployees;

    @JsonProperty (value = "currentStudents")
    private List <PersonDto> CurrentStudents;

   /* @JsonProperty (value = "place")
    private PlaceDto place;*/

    @JsonProperty (value = "equipment")
    private String equipment;


}
