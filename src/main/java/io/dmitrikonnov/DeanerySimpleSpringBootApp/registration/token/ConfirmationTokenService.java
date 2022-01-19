package io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken (ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public Optional <ConfirmationToken> findTokenByUserId (Long userId) {
        return confirmationTokenRepository.findFirstByUserEntityIdOrderByIdDesc(userId);}

    public void deleteAllWhereUserEnableAfterCreated (LocalDateTime createdAt) {
        confirmationTokenRepository.deleteAllWhereUserEnableAfterCreated(createdAt);

    }

    public int setConfirmedAt (String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
    public List<ConfirmationToken> getAllTokensOfDisabledUsers() {
        return confirmationTokenRepository.findAllTokensOfDisabledUsers();
    }
    public void deleteTokenById (Long id) {
        confirmationTokenRepository.deleteById(id);
    }

}
