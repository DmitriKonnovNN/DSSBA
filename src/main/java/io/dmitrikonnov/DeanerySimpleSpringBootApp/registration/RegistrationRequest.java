package io.dmitrikonnov.DeanerySimpleSpringBootApp.registration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private Date date;

    @JsonCreator (mode = JsonCreator.Mode.PROPERTIES)
    public RegistrationRequest (@JsonProperty("firstName")String firstName,
                                @JsonProperty("lastName") String lastName,
                                @JsonProperty("email") String email,
                                @JsonProperty("password") String password) {
        this(firstName,
                lastName,
                email,
                password,
                new Date (System.currentTimeMillis()));
    }


}
