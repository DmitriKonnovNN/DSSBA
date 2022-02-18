package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoGetDetails;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoUpdateRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service ("userServiceImpl")
@Transactional
@AllArgsConstructor (onConstructor_ ={@Autowired})
public class UserServiceImpl implements UserDetailsService, UserService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final static String USER_ID_NOT_FOUND_MSG = "user with id %d not found";


    @Value("${userServiceImpl.tokenExpirationTime}") public final int DEFAULT_TOKEN_EXPIRATION_MINUTES;
    @Value("${userServiceImpl.tokenExpirationTime}") private int TOKEN_EXPIRATION_MINUTES;

    private final UserRepository userRepository;
    private final SignUpUserAndGetToken<String,UserEntity> signUpUserAndGetTokenImpl;

    @Override
    public boolean checkIfExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDtoGetDetails getDetails(Long id) {
        return userRepository.getUserEntityById(id);
    }


    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserRole(UserDtoUpdateRole dto) {
        userRepository.updateUserRole(dto.getId(), dto.getRole());
    }

    @Override
    public void deleteById (Long id){
        userRepository.deleteById(id);
    }


    public int getTokenExpiration() {
        return TOKEN_EXPIRATION_MINUTES;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findUserEntityByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    @Override
    public void enableUser (String email){
         userRepository.enableUserEntityByEmail(email);
    }

    @Override
    public void setExpirationTimeOfToken(int minutes) {
        TOKEN_EXPIRATION_MINUTES = minutes;
    }

    @Override
    public int resetExpirationTimeOfToken() {
        return TOKEN_EXPIRATION_MINUTES = DEFAULT_TOKEN_EXPIRATION_MINUTES;
    }

    /**
 * If email {
     * is occupied (==user is enabled), throw Exception.
 * If a token for particular email has been generated yet, check whether it's expired.
 * If not expired, throw Exception.
 * If expired, generate a new token and update first name, last name and password.
     * }
     *
     * If email ain't occupied, persist it and generate a token for signing up.
     *
     * @param userEntity UserEntity to be persisted
     * @return a UUID-token
     * @throws IllegalStateException if email is occupied, which means user is enabled, or if the previous token
     * hasn't expired yet
     *
     *
 * */

    @Override
    public String signUpUserAndGetToken(UserEntity userEntity) {
        return signUpUserAndGetTokenImpl.signUpUserAndGetToken(userEntity);
    }

}
