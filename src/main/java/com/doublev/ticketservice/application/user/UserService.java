package com.doublev.ticketservice.application.user;

import com.doublev.ticketservice.application.user.dto.*;
import com.doublev.ticketservice.domain.user.User;
import com.doublev.ticketservice.domain.user.port.UserRepositoryPort;
import com.doublev.ticketservice.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepositoryPort userRepository;

    public UserService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse create(CreateUserRequest request) {
        User user = User.newUser(request.nombres(), request.apellidos());
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserResponse update(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        user.update(request.nombres(), request.apellidos());
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    public UserResponse getById(UUID id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getNombres(),
                user.getApellidos(),
                user.getFechaCreacion(),
                user.getFechaActualizacion()
        );
    }
}
