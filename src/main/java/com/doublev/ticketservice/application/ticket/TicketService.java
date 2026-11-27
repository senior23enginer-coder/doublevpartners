package com.doublev.ticketservice.application.ticket;

import com.doublev.ticketservice.application.ticket.dto.*;
import com.doublev.ticketservice.domain.ticket.*;
import com.doublev.ticketservice.domain.ticket.port.TicketRepositoryPort;
import com.doublev.ticketservice.shared.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepositoryPort ticketRepository;

    public TicketService(TicketRepositoryPort ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @CacheEvict(value = "ticketsPorUsuario", key = "#request.userId")
    public TicketResponse create(CreateTicketRequest request) {
        Ticket ticket = Ticket.newTicket(
                request.descripcion(),
                request.userId(),
                request.status()
        );
        Ticket saved = ticketRepository.save(ticket);
        return toResponse(saved);
    }

    public TicketResponse getById(UUID id) {
        return ticketRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
    }

    @CacheEvict(value = "ticketsPorUsuario", key = "#result.userId", condition = "#result != null")
    public TicketResponse update(UUID id, UpdateTicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
        ticket.update(request.descripcion(), request.status());
        Ticket saved = ticketRepository.save(ticket);
        return toResponse(saved);
    }

    @CacheEvict(value = "ticketsPorUsuario", key = "#userId")
    public void delete(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket no encontrado"));
        UUID userId = ticket.getUserId();
        ticketRepository.deleteById(id);
    }

    public Page<TicketResponse> list(TicketStatus status, UUID userId, Pageable pageable) {
        Page<Ticket> page;
        if (status != null && userId != null) {
            page = ticketRepository.findByStatusAndUserId(status, userId, pageable);
        } else if (status != null) {
            page = ticketRepository.findByStatus(status, pageable);
        } else if (userId != null) {
            page = ticketRepository.findByUserId(userId, pageable);
        } else {
            page = ticketRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    @Cacheable(value = "ticketsPorUsuario", key = "#userId")
    public List<TicketResponse> getTicketsByUser(UUID userId) {
        return ticketRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getDescripcion(),
                ticket.getUserId(),
                ticket.getStatus(),
                ticket.getFechaCreacion(),
                ticket.getFechaActualizacion()
        );
    }
}
