package io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.Person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.Dto.BasicDto;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.annotation.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel (description = "Details about the person")
public class PersonDto extends BasicDto {


   @JsonProperty(value = "FirstName", defaultValue = "No first name",
         access = JsonProperty.Access.READ_WRITE)

    @ApiModelProperty (notes = "the person's name", required = true)
    @NotEmpty (message = "first name cannot be empty")
    private String firstName;

    @JsonProperty (value = "SecondName")
    private String secondName;

    @JsonProperty(value = "LastName")
    private String lastName;

    @JsonProperty (value = "Birthday")
    private Date birthday;

    @JsonProperty (value = "Sex")
    private String sex;

    @JsonProperty (value = "E_mail")
    private String email;

    @JsonProperty (value = "PhoneNumber")
    private String phoneNumber;

    @JsonProperty (value = "Login")
    private String login;

    @JsonProperty (value = "Password")
    private String password;



}
