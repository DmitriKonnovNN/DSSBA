package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.AccountDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Appointment.ExamDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import lombok.*;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@DTO
public class EmployeeDto extends PersonDto {

    @JsonProperty ("ACCOUNTS")
    private Set<AccountDto> accounts;

    @JsonProperty ("EXAMS")
    private Set<ExamDto> exams;
}