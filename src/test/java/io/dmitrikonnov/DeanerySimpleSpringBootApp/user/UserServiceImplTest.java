package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.configuration.ConfigurationProperties;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationToken;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationTokenRepository;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationTokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class UserServiceImplTest {



    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepositoryMock;

    private UserServiceImpl underTest;

    private Long testId1 = 1L;
    private int TEST_token_exp_minutes = 2;
    private int TEST_default_token_exp_minutes = 2;


    @BeforeEach
    void setUp() {
                underTest = new UserServiceImpl(TEST_default_token_exp_minutes,
                TEST_token_exp_minutes,
                userRepositoryMock,
                new BCryptPasswordEncoder(),
                new ConfirmationTokenService(confirmationTokenRepositoryMock));


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldReturnTrueIfExistsInDB() {
        given(userRepositoryMock.existsById(anyLong())).willReturn(true);
        assertThat(underTest.checkIfExist(anyLong())).isTrue();

    }

    @Test
    void shouldReturnFalseIfExistsInDB() {
        given(userRepositoryMock.existsById(anyLong())).willReturn(false);
        assertThat(underTest.checkIfExist(anyLong())).isFalse();

    }

    @Test
    void shouldGetDetailsOfUserById() {
        underTest.getDetails(testId1);
        verify(userRepositoryMock).getUserEntityById(testId1);

    }

    @Test
    void findAll() {
    }

    @Test
    void updateUserRole() {

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mock_id.csv",numLinesToSkip = 1)
    void shouldDeleteById(Long inputId, Long expectedId) {
        underTest.deleteById(inputId);
        verify(userRepositoryMock).deleteById(expectedId);
    }

    @Test
    void ShouldGetTokenExpiration() {
        assertThat(underTest.getTokenExpiration()).isEqualTo(TEST_token_exp_minutes);
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void enableUser() {
    }


    @ParameterizedTest
    @ValueSource(ints ={1,3,5,-4, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void ShouldSetExpirationTimeOfToken(int inputExpirationTime) {

        underTest.setExpirationTimeOfToken(inputExpirationTime);
        assertThat(underTest.getTokenExpiration()).isEqualTo(inputExpirationTime);


    }

    @ParameterizedTest
    @ValueSource(ints ={1,3,5,-4, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void ShouldResetExpirationTimeOfTokenToDefaultValue(int inputExpirationTime) {

        underTest.setExpirationTimeOfToken(inputExpirationTime);
        assertThat(underTest.resetExpirationTimeOfToken()).isEqualTo(TEST_default_token_exp_minutes);

    }

    @Test
    void signUpUser() {
    }
}