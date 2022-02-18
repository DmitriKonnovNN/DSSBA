package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;
/**
 * T represents type of returned token
 * U represents type of an user to be signed up */

public interface SignUpUserAndGetToken <T,U>{


    T signUpUserAndGetToken(U u);
}
