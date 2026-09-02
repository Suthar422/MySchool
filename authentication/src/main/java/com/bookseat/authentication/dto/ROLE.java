package com.bookseat.authentication.dto;

import java.util.Set;

public enum ROLE {
    ADMIN(Set.of(Permissions.READ, Permissions.WRITE, Permissions.DELETE)),
    STAFF(Set.of(Permissions.READ, Permissions.WRITE)),
    STUDENT(Set.of(Permissions.READ));


    private final Set<Permissions> permissions;

    ROLE(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
