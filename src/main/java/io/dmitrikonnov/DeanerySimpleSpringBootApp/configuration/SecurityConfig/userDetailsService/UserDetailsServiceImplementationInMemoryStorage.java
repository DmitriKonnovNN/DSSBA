package io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.SecurityConfig.userDetailsService;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.SecurityConfig.PasswordEncoder;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;


@Component("inMemory")
@AllArgsConstructor
@Lazy
public class UserDetailsServiceImplementationInMemoryStorage implements UserDetailsServiceResolverInterface {


    PasswordEncoder bCryptPasswordEncoder;

    @Override
    @Bean  (name = "userDetailsServiceInMemory")
    public UserDetailsService getUserDetailsService() {
        UserDetails user = User.builder()
                .username("dima")
                .password(bCryptPasswordEncoder.bCryptPasswordEncoder().encode("123"))
                .authorities(UserRole.USER.getAuthorities())
                .build();

        UserDetails user1 = User.builder()
                .username("denis")
                .password(bCryptPasswordEncoder.bCryptPasswordEncoder().encode("123"))
                .authorities(UserRole.ADMIN.getAuthorities())
                .build();
        UserDetails user2 = User.builder()
                .username("kirill")
                .password(bCryptPasswordEncoder.bCryptPasswordEncoder().encode("123"))
                .authorities(UserRole.SUPERADMIN.getAuthorities())
                .build();

        return new InMemoryUserDetailsManager(user,user1, user2);
    }
}
