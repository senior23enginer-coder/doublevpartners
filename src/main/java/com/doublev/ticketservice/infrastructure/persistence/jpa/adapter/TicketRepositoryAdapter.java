package com.doublev.ticketservice.infrastructure.persistence.jpa.adapter;

import com.doublev.ticketservice.domain.ticket.*;
import com.doublev.ticketservice.domain.ticket.port.TicketRepositoryPort;
import com.doublev.ticketservice.infrastructure.persistence.jpa.entity.TicketJpaEntity;
import com.doublev.ticketservice.infrastructure.persistence.jpa.repository.SpringDataTicketRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TicketRepositoryAdapter implements TicketRepositoryPort {

    private final SpringDataTicketRepository repository;

    public TicketRepositoryAdapter(SpringDataTicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketJpaEntity entity = toEntity(ticket);
        TicketJpaEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDomain);
    }

    @Override
    public Page<Ticket> findByStatus(TicketStatus status, Pageable pageable) {
        return repository.findByStatus(status, pageable).map(this::toDomain);
    }

    @Override
    public Page<Ticket> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(this::toDomain);
    }

    @Override
    public Page<Ticket> findByStatusAndUserId(TicketStatus status, UUID userId, Pageable pageable) {
        return repository.findByStatusAndUserId(status, userId, pageable)
                .map(this::toDomain);
    }

    @Override
    public List<Ticket> findByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .toList();
    }

    private TicketJpaEntity toEntity(Ticket ticket) {
        TicketJpaEntity entity = new TicketJpaEntity();
        entity.setId(ticket.getId());
        entity.setDescripcion(ticket.getDescripcion());
        entity.setUserId(ticket.getUserId());
        entity.setStatus(ticket.getStatus());
        entity.setFechaCreacion(ticket.getFechaCreacion());
        entity.setFechaActualizacion(ticket.getFechaActualizacion());
        return entity;
    }

    private Ticket toDomain(TicketJpaEntity entity) {
        return new Ticket(
                entity.getId(),
                entity.getDescripcion(),
                entity.getUserId(),
                entity.getStatus(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
