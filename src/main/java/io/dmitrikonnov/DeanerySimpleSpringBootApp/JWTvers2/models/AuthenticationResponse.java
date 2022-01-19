package io.dmitrikonnov.DeanerySimpleSpringBootApp.JWTvers2.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwt;


}
