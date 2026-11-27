package com.doublev.ticketservice.application.ticket.dto;

import com.doublev.ticketservice.domain.ticket.TicketStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


public record TicketResponse(
        UUID id,
        String descripcion,
        UUID userId,
        TicketStatus status,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {}
