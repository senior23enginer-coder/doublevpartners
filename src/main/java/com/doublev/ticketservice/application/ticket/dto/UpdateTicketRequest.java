package com.doublev.ticketservice.application.ticket.dto;

import com.doublev.ticketservice.domain.ticket.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTicketRequest(
        @NotBlank @Size(max = 500) String descripcion,
        @NotNull TicketStatus status
) {}
