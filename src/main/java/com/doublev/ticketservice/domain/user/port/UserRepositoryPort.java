package com.doublev.ticketservice.domain.user.port;


import com.doublev.ticketservice.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    void deleteById(UUID id);
}
