package com.bookseat.authentication.config;

import com.bookseat.authentication.dto.ROLE;
import com.bookseat.authentication.entity.Users;
import jakarta.persistence.SecondaryTable;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Permission;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private Users user;
    private ROLE role;


    public UserPrincipal(Users user) {
        this.user = user;
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        //assigning role and permissions
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));

        //extracting permissions from the role
        Set<SimpleGrantedAuthority> permissionsAuthorities = role.getPermissions().stream()
                .map(Permission -> new SimpleGrantedAuthority(Permission.name()))
                .collect(Collectors.toSet());

        //adding permissions to the authorities
        authorities.addAll(permissionsAuthorities);

        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    public String getRole() {
        return user.getRole().name();
    }

}
