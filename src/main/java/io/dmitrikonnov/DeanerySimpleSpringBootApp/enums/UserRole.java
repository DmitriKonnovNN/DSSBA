package io.dmitrikonnov.DeanerySimpleSpringBootApp.enums;


import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static io.dmitrikonnov.DeanerySimpleSpringBootApp.enums.UserPermission.*;


public enum UserRole {

    USER(Sets.newHashSet(COURSE_READ, TOPIC_READ)),
    ADMIN(Sets.newHashSet(COURSE_READ, TOPIC_READ, COURSE_WRITE, TOPIC_WRITE, USER_READ)),
    SUPERADMIN (Sets.newHashSet(USER_READ,USER_WRITE,COURSE_ALL,TOPIC_ALL));


    private final Set <UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<? extends GrantedAuthority> getAuthorities () {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}
