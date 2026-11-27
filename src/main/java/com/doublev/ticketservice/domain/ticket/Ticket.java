package com.doublev.ticketservice.domain.ticket;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Ticket {

    private UUID id;
    private String descripcion;
    private UUID userId;
    private TicketStatus status;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Ticket(UUID id, String descripcion, UUID userId,
                  TicketStatus status, LocalDateTime fechaCreacion,
                  LocalDateTime fechaActualizacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.userId = userId;
        this.status = status;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static Ticket newTicket(String descripcion, UUID userId, TicketStatus status) {
        LocalDateTime now = LocalDateTime.now();
        return new Ticket(null, descripcion, userId, status, now, now);
    }

    public void update(String descripcion, TicketStatus status) {
        this.descripcion = descripcion;
        this.status = status;
        this.fechaActualizacion = LocalDateTime.now();
    }


}
