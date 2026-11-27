package com.doublev.ticketservice.application.ticket.dto;

import com.doublev.ticketservice.domain.ticket.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTicketRequest(
        @NotBlank @Size(max = 500) String descripcion,
        @NotNull UUID userId,
        @NotNull TicketStatus status
) {}


