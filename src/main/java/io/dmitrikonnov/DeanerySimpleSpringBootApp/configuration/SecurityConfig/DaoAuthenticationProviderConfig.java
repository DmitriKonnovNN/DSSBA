package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.SecurityConfig;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.SecurityConfig.userDetailsService.UserDetailsServiceQualifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DaoAuthenticationProviderConfig  {

    PasswordEncoder bCryptPasswordEncoder;
    UserDetailsServiceQualifierResolver resolver;
    UserDetailsService userDetailsService;
    Logger log = LoggerFactory.getLogger(this.getClass());
    private final String DAO_PROV_CONF_MSG = "Current Dao Authentication Provider Configuration : %s";


    public DaoAuthenticationProviderConfig(@Autowired PasswordEncoder bCryptPasswordEncoder,
                                           @Value ("${appSecurity.daoAuthenticationProvider}") String qualifier,
                                           @Autowired UserDetailsServiceQualifierResolver resolver) {
        this.resolver = resolver;
        this.userDetailsService = resolver.resolveQualifier(qualifier);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        log.warn(String.format(DAO_PROV_CONF_MSG, qualifier));

    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider (){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
