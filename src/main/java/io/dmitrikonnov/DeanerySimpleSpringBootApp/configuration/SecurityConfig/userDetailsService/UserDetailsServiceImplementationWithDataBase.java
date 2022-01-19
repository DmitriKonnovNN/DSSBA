package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.SecurityConfig.userDetailsService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component("inDataBase")
@AllArgsConstructor (onConstructor_ = {@Autowired} )
public class UserDetailsServiceImplementationWithDataBase implements UserDetailsServiceResolverInterface {

    @Qualifier ("userServiceImpl")
    private final UserServiceImpl userServiceImpl;


    @Override
    public UserDetailsService getUserDetailsService() {
        return userServiceImpl;
    }
}
