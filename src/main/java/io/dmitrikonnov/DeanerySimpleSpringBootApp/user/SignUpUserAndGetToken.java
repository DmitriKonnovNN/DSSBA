package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;


public interface SignUpUserAndGetToken <T,U>{
    T signUpUserAndGetToken(U u);
}
