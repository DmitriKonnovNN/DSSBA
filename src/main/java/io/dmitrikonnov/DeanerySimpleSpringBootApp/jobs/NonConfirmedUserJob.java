package io.dmitrikonnov.DeanerySimpleSpringBootApp.jobs;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationTokenService;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserEntity;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.UserService;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Deletes lines: confirmation_token and user_entity from the Data Base depending on following factors:
 * whether {@link UserEntity} is enabled and {@link ConfirmationToken} is confirmed*/

@Component
@AllArgsConstructor
public class NonConfirmedUserJob {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    private final static Logger log = LoggerFactory.getLogger(NonConfirmedUserJob.class);
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final static String SCHEDULED_JOB_REGISTRATION_MGS = "Scheduled registration job started at {}";
    private final static String DELETION_STARTED_MSG = "Scheduled deletion started at {} : Delete tokens of confirmed users.";
    private final static String DELETION_CONF_USER_COMPLETED = "Tokens of confirmed users deleted";
    private final static String DELETION_EXPIRED_TOKEN_MSG = "Scheduled deletion started at {} : Delete expired token.";

    private final static Long DEL_TIME_UNCONF_USER = 3L;
    private final static Long DEL_TIME_CONF_USER = 10L;
    private final static Long TIMESPAN_MULTIPLIER = 1L;





    @Scheduled(cron ="0 */30 * * * SUN")
    protected void deleteNonConfirmedUser (){
        deleteTokenForConfirmedUser();
        log.info(DELETION_EXPIRED_TOKEN_MSG, dateFormat.format(new Date()));

        int expirationMinutes = userService.getTokenExpiration();



        boolean userWaitForConfirmation = false;

       List<ConfirmationToken> tokensOfDisabledUser = confirmationTokenService.getAllTokensOfDisabledUsers();
       for (int i = 0; i < tokensOfDisabledUser.size(); i++) {
           log.warn("Iterator: " + i + " tokensOfDisabledUser SIZE " + tokensOfDisabledUser.size());

           ConfirmationToken token = tokensOfDisabledUser.get(i);
           LocalDateTime expiresAt = token.getExpiresAt();


           if (expiresAt.isBefore(LocalDateTime.now().minusMinutes(DEL_TIME_UNCONF_USER))) {
               confirmationTokenService.deleteTokenById(token.getId());
               log.warn("Token deleted (EXPIRED 2 DAYS AGO) by id: " + token.getId());

               if (!userWaitForConfirmation) {
                   if (i + 1 == tokensOfDisabledUser.size() || !tokensOfDisabledUser.get(i + 1).getUserEntity().getId().equals(token.getUserEntity().getId())) {
                       userService.deleteById(token.getUserEntity().getId());
                       log.warn("Disabled user deleted by id: " + token.getUserEntity().getId());

                   }
               }

               if (userWaitForConfirmation) {
                   if (i + 1 == tokensOfDisabledUser.size() || !tokensOfDisabledUser.get(i + 1).getUserEntity().getId().equals(token.getUserEntity().getId())) {
                       userWaitForConfirmation = false;
                       log.info("User waits for confirmation SET FALSE");
                   }
               }
           }
           if (expiresAt.isAfter(LocalDateTime.now().minusMinutes(DEL_TIME_UNCONF_USER))){
               if (expiresAt.isAfter(LocalDateTime.now().minusMinutes(expirationMinutes * TIMESPAN_MULTIPLIER)))
               {userWaitForConfirmation = true;
               log.warn("User waits for confirmation SET TRUE");
               continue;}

               confirmationTokenService.deleteTokenById(token.getId());
               log.warn("Token deleted (EXPIRED 1 DAY AGO) by id "+token.getId());
               userWaitForConfirmation = true;
               log.warn("User waits for confirmation SET TRUE");
           }
       }

       debug();
    }

   /**
    * */
    private void deleteTokenForConfirmedUser (){
        {
            log.info(SCHEDULED_JOB_REGISTRATION_MGS, dateFormat.format(new Date()));
            log.info(DELETION_STARTED_MSG, dateFormat.format(new Date()));

            debug();
            System.out.println("STEP 1 before deleteAllWhereUserEnable");
        }
        confirmationTokenService.deleteAllWhereUserEnableAfterCreated(LocalDateTime.now().minusMinutes(DEL_TIME_CONF_USER));


        {
            System.out.println("STEP 2 after deleteAllWhereUserEnable");
            debug();
            log.info(DELETION_CONF_USER_COMPLETED);
        }
    }

 /**
  * private void debug ()
  * This method is just for debugging in here. Delete its invocations after debug succeeded!
  * */
    private void debug(){
        int [] counterTokens = {0};
        int [] counterUsers = {0};
        List<ConfirmationToken> tokensOfDisabledUser = confirmationTokenService.getAllTokensOfDisabledUsers();
        tokensOfDisabledUser.forEach(c->{ System.out.print("token ID: " + c.getId() + " " );
            System.out.print("token EXPIRES AT: " + c.getExpiresAt() + " ");
            System.out.print("user ID: " + c.getUserEntity().getId());
            System.out.print(" user is enable: " + c.getUserEntity().getEnabled());
            System.out.println();
            counterTokens[0]++;});

        System.out.println("Lines present: " + counterTokens[0]);

        List<UserEntity> users = userService.findAll();
        users.forEach(u->{
            System.out.print("user ID: " + u.getId() + " ");
            System.out.print("user ENABLE: " + u.getEnabled() + " ");
            System.out.println();
            counterUsers[0]++;
        });

        System.out.println("Users present: " + counterUsers[0]);

    }


}
