package com.HandsUp.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.HandsUp.entities.Azienda;
import com.HandsUp.entities.Utente;

public class DatabaseUserDetails implements UserDetails {

    private Utente user;
    private Azienda company;
    private Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(Utente user) {
        this.user = user;
        this.authorities = user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toSet());
    }

    public DatabaseUserDetails(Azienda company) {
        this.company = company;
        this.authorities = new HashSet<>();
        // Imposta le autorità di default per l'azienda, se necessario
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        if (user != null) {
            return user.getPassword();
        } else {
            return company.getPassword();
        }
    }

    @Override
    public String getUsername() {
        if (user != null) {
            return user.getEmail();
        } else {
            return company.getEmail();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

