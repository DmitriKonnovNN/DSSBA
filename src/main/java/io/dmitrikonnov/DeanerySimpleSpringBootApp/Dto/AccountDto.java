package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person.EmployeeDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@DTO
public class AccountDto extends BasicDto {

    @NotEmpty (message = "account number cannot be empty")
    @JsonProperty ("ACC_NUMBER")
    private String accountNumber;

    @JsonProperty ("EMPLOYEE")
    private EmployeeDto employee;

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();
}
