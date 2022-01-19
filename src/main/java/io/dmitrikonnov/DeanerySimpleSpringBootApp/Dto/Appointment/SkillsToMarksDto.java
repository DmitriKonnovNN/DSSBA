package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.BasicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import lombok.Data;

import java.util.Map;

@Data
@DTO
public class SkillsToMarksDto extends BasicDto {

    @JsonProperty (value = "skillsToMarks")
    public Map<String, String> skillsToMarks;
}
