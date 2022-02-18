package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationToken;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Service
@AllArgsConstructor(onConstructor_ ={@Autowired})
public class SignUpUserAndGetUUIDTokenImpl implements SignUpUserAndGetToken <String, UserEntity>{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Value("${userServiceImpl.tokenExpirationTime}") private int TOKEN_EXPIRATION_MINUTES;
    private final static String TOKEN_HAS_NOT_EXPIRED_MSG = "token hasn't expired yet";
    private final static String EMAIL_ALREADY_OCCUPIED_MSG = "email %s already occupied";

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
