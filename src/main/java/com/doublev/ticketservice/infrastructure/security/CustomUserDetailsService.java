package com.doublev.ticketservice.infrastructure.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return User.builder()
                .username("admin")
                .password("admin123")
                .authorities(List.of(() -> "ROLE_ADMIN"))
                .build();
    }
}
