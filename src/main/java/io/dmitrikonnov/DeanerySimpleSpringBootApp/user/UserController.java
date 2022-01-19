package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoGetDetails;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoUpdateRole;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.exception.BadRequestParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.websocket.server.PathParam;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping ("api/v1/users")
@AllArgsConstructor
@Api (value = "User REST controller")
public class UserController {

    private final UserService userService;
    private static final String ID_NF_UPDATE_FAIL_MSG = "id %d not found. Update failed.";
    private final static String ID_NOT_FOUND_MSG = "id %d not found.";

    @ApiOperation(value = "get details on a particular student",
    httpMethod = "GET",
   /* tags = "getDetails",*/
    response = UserDtoGetDetails.class)
    @GetMapping("/{id}/details")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<UserDtoGetDetails> getDetails (
            @ApiParam (
                    name = "id",
                    type = "Long",
                    value = "id of the user",
                    required = true
            )
            @PathVariable Long id){
        if (!userService.checkIfExist(id)){
            throw new NoSuchElementException(String.format(ID_NOT_FOUND_MSG, id));}
        return ResponseEntity.status(HttpStatus.OK).body(userService.getDetails(id));
    }

    @ApiOperation(value = "update user's role by its id",
    httpMethod = "PUT"
    /*tags = "updateRole"*/)
    @PutMapping("/{id}/update/role")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    public void updateRole (
            @ApiParam (name = "id",
            type = "Long",
            value = "id of the user",
            required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    description ="role-update's model",
            required = true,
            content = @Content(schema = @Schema(implementation = UserDtoUpdateRole.class)))
            @RequestBody @Validated UserDtoUpdateRole userDto){
        if (!userService.checkIfExist(id)){
            throw new NoSuchElementException(String.format(ID_NF_UPDATE_FAIL_MSG, id));}
        userDto.setId(id);
        userService.updateUserRole(userDto);
    }

    @ApiOperation(value = "set time within the confirmation token is valid",
    httpMethod = "PUT",
    /*tags = "setConfirmationTokenExpirationTime",*/
    notes = "Expiration time's MAX VALUE is 30 days, MIN VALUE is 1 minute.")
    @PutMapping("/conftoken/exptime")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN') and hasAnyAuthority('user:write')")
    @Validated
    public void setConfirmationTokenExpirationTime  (
            @ApiParam (
                    name = "minutes",
                    type = "int",
                    value = "expiration time",
                    required = true
            )
            @PathParam (value = "minutes") Optional<Integer> minutes ) {

        userService.setExpirationTimeOfToken(minutes.get());
    }
    //TODO: figure out why Validation with @Validated + @PathParam || @PathVariable + @Min ain't working

    // hasRole ('ROLE_') hasAnyRole ('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')
    @PutMapping("/conftoken/resettime")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public int resetConfirmationTokenExpirationTime (){
        return userService.resetExpirationTimeOfToken();
    }

    @GetMapping ("/conftoken/exptime")
    @PreAuthorize(" hasAnyAuthority('user:read')")
    public int getConfirmationTokenExpirationTime () {
        return userService.getTokenExpiration();
    }

    @ExceptionHandler (MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String>noMatchingParameters1(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad type of param: "+ ex.getValue()
                +" .Expiration time should be given in Minutes. Min value = 1.");
    }
}