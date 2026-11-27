package com.doublev.ticketservice.application.user.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String nombres,
        String apellidos,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {}
