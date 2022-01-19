package io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence")
    private Long id;
    @Column (nullable = false)
    private String token;
    @Column (nullable = false)
    private LocalDateTime createdAt;
    @Column (nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "user_id")
    private UserEntity userEntity;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiredAt,
                             UserEntity userEntity) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiredAt;
        this.userEntity = userEntity;
    }
}
