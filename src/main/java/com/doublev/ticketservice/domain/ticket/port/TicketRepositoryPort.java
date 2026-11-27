package com.doublev.ticketservice.domain.ticket.port;

import com.doublev.ticketservice.domain.ticket.Ticket;
import com.doublev.ticketservice.domain.ticket.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepositoryPort {

    Ticket save(Ticket ticket);

    Optional<Ticket> findById(UUID id);

    void deleteById(UUID id);

    Page<Ticket> findAll(Pageable pageable);

    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);

    Page<Ticket> findByUserId(UUID userId, Pageable pageable);

    Page<Ticket> findByStatusAndUserId(TicketStatus status, UUID userId, Pageable pageable);

    List<Ticket> findByUserId(UUID userId);
}
