package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoGetDetails;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoUpdateRole;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationToken;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service ("userServiceImpl")
@Transactional
@AllArgsConstructor (onConstructor_ ={@Autowired})
public class UserServiceImpl implements UserDetailsService, UserService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final static String USER_ID_NOT_FOUND_MSG = "user with id %d not found";
    private final static String TOKEN_HAS_NOT_EXPIRED_MSG = "token hasn't expired yet";
    private final static String EMAIL_ALREADY_OCCUPIED_MSG = "email %s already occupied";
    @Value("${userServiceImpl.tokenExpirationTime}") public final int DEFAULT_TOKEN_EXPIRATION_MINUTES;
    @Value("${userServiceImpl.tokenExpirationTime}") private int TOKEN_EXPIRATION_MINUTES;


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

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
        Set<String> tokens = new HashSet<>();

        userRepository.findUserEntityByEmail(userEntity.getEmail())
                .ifPresentOrElse((persistedUser)-> {
                    if(persistedUser.isEnabled()){
                        throw new IllegalStateException(String.format(EMAIL_ALREADY_OCCUPIED_MSG,userEntity.getEmail()));}
                    if(!persistedUser.isEnabled()){
                        tokens.addAll(getNewTokenIfOlderExpired(userEntity,persistedUser));}

                },()-> tokens.addAll(getTokenForNotPersistedUser(userEntity)));
        return tokens.iterator().next();

    }

    private Set<String> getTokenForNotPersistedUser(UserEntity userEntity){
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return Collections.singleton(generateToken(userEntity));
    }

    private Set<String> getNewTokenForPersistedUser (UserEntity userEntity, UserEntity persistedUser) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userRepository.updateUserEntityByEmail(
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getPassword());
        return Collections.singleton(generateToken(persistedUser));

    }

    private Set<String> getNewTokenIfOlderExpired(UserEntity userEntity,UserEntity persistedUser) {
        if(confirmationTokenService.findTokenByUserId(persistedUser.getId()).isPresent()) {
            if (isTokenNotExpired(persistedUser)) throw new IllegalStateException(TOKEN_HAS_NOT_EXPIRED_MSG);

        }
        return getNewTokenForPersistedUser(userEntity,persistedUser);

    }

    private boolean isTokenNotExpired(UserEntity persistedUser) {

            if(confirmationTokenService
                    .findTokenByUserId(persistedUser.getId())
                    .get()
                    .getExpiresAt()
                    .isAfter(LocalDateTime.now())){
                return true; }
            return false;
    }



    private String generateToken(UserEntity userEntity){
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES),
                userEntity
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }


}
