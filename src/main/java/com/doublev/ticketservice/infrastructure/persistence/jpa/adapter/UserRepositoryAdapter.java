package com.doublev.ticketservice.infrastructure.persistence.jpa.adapter;

import com.doublev.ticketservice.domain.user.User;
import com.doublev.ticketservice.domain.user.port.UserRepositoryPort;
import com.doublev.ticketservice.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.doublev.ticketservice.infrastructure.persistence.jpa.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository repository;

    public UserRepositoryAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = toEntity(user);
        UserJpaEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private UserJpaEntity toEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setNombres(user.getNombres());
        entity.setApellidos(user.getApellidos());
        entity.setFechaCreacion(user.getFechaCreacion());
        entity.setFechaActualizacion(user.getFechaActualizacion());
        return entity;
    }

    private User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
