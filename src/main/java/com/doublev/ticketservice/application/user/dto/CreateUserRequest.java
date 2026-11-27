package com.doublev.ticketservice.application.user.dto;


import jakarta.validation.constraints.NotBlank;


public record CreateUserRequest(
        @NotBlank String nombres,
        @NotBlank String apellidos
) {}
