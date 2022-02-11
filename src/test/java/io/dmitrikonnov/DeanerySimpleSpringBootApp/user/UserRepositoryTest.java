package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.enums.UserRole;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoGetDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @BeforeEach
    void setUp() {
        UserEntity user1 = new UserEntity("Dmitri",
                "Konnov",
                "123",
                "dmitri@mail.ru",
                UserRole.USER);
        underTest.save(user1);

    }

    @AfterEach
    void tearDown() {

    }


    @Test
    void getUserEntityById() {
        UserDtoGetDetails user1 = new UserDtoGetDetails();
        user1.setFirstName("Dmitri");
        user1.setLastName("Konnov");
        user1.setEmail("dmitri@mail.ru");
        user1.setRole(UserRole.USER);
        user1.setId(1L);
        user1.setLocked(false);
        user1.setEnabled(false);



        assertThat(underTest.getUserEntityById(1L)).isEqualTo(user1);
    }

    @Test
    void updateUserRole() {



    }

    @Test
    void updateUserEntityByEmail() {
    }

    @Test
    void enableUserEntityByEmail() {
    }

    @Test
    void deleteUserEntities() {
    }
}