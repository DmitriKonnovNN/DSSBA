package io.dmitrikonnov.DeanerySimpleSpringBootApp.user;

import io.dmitrikonnov.DeanerySimpleSpringBootApp.user.userDto.UserDtoGetDetails;
import io.dmitrikonnov.DeanerySimpleSpringBootApp.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@SqlResultSetMapping(name = "UserDetails",
        classes = @ConstructorResult(targetClass = UserDtoGetDetails.class,
                columns = { @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name ="role", type = String.class),
                        @ColumnResult(name ="first_name", type = String.class),
                        @ColumnResult(name ="last_name", type = String.class),
                        @ColumnResult(name ="email", type = String.class),
                        @ColumnResult(name ="active", type = Boolean.class),
                        @ColumnResult(name ="locked", type = Boolean.class),
                        @ColumnResult(name ="enabled", type = Boolean.class )} ) )
@NamedNativeQueries({@NamedNativeQuery(name = "getUserDetails",
        query = "select id, role, first_name, last_name, email, active, locked, enabled from user_entity where id = :id",
        resultSetMapping = "UserDetails" )})
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
//@Table (name = "\"USER\"")
public class UserEntity implements UserDetails {

    @Id
    @Column (name = "id", unique = true, nullable = false, updatable = false)
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE,
            generator = "student_sequence")
    private Long id;

    
    private String firstName;
    private String lastName; 
    private String password;
    private String email;
    private Boolean active;
    private Boolean locked = false;
    private Boolean enabled = false;
    @Enumerated (EnumType.STRING)
    private UserRole role;

    public UserEntity(String firstName,
                      String lastName,
                      String password,
                      String email,
                      UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    private boolean isActive() {
        return active;
    }


    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        //SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
