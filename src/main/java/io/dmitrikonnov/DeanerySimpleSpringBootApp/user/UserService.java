package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoGetDetails;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoUpdateRole;

import java.util.List;

public interface UserService {
    public void deleteById (Long id);
    public void enableUser (String email);
    public String signUpUserAndGetToken(UserEntity userEntity);
    public List<UserEntity> findAll ();
    public int getTokenExpiration();
    void updateUserRole(UserDtoUpdateRole dto);
    boolean checkIfExist(Long id);
    public UserDtoGetDetails getDetails (Long id);
    public void setExpirationTimeOfToken (int minutes);
    public int resetExpirationTimeOfToken ();

}
