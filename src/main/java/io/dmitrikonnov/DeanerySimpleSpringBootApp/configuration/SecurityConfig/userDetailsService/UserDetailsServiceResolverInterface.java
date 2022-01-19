package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.SecurityConfig.userDetailsService;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailsServiceResolverInterface {

    public UserDetailsService getUserDetailsService();
}
