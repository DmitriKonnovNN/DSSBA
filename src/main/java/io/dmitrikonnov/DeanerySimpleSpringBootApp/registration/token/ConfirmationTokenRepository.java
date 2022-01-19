package io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository <ConfirmationToken,Long> {

    Optional<ConfirmationToken>findByToken (String token);

    Optional<ConfirmationToken> findFirstByUserEntityIdOrderByIdDesc (Long userId);

    @Transactional
    @Modifying
    @Query ("UPDATE ConfirmationToken c " +
    "SET c.confirmedAt = ?2 " +
    "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query (value = "delete ct from confirmation_token ct "+
            "left join user_entity u on ct.user_id=u.id where (u.enabled=true) and (ct.created_at< :createdAt) ", nativeQuery = true)
    void deleteAllWhereUserEnableAfterCreated(LocalDateTime createdAt);

    @Query ("select c from ConfirmationToken as c "+
            " join UserEntity as u on c.userEntity.id = u.id "+
            "where u.enabled = false " +
            " order by u.id asc, c.expiresAt desc")
    List<ConfirmationToken> findAllTokensOfDisabledUsers();


    /*@Query (value = "select u.id as userid, c.id as tokenid, c.expires_at"+
            " from confirmation_token as c, user_entity as u"+
            " where c.user_id = u.id" +
            " order by u.id desc", nativeQuery = true)
    List<String> getAllTokens();*/
}
