package com.doublev.ticketservice.application.user.dto;


import jakarta.validation.constraints.NotBlank;


public record UpdateUserRequest(
        @NotBlank String nombres,
        @NotBlank String apellidos
) {}