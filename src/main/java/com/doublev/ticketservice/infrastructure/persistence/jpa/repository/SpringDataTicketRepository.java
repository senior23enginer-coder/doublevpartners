package com.doublev.ticketservice.infrastructure.persistence.jpa.repository;

import com.doublev.ticketservice.domain.ticket.TicketStatus;
import com.doublev.ticketservice.infrastructure.persistence.jpa.entity.TicketJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataTicketRepository extends JpaRepository<TicketJpaEntity, UUID> {

    Page<TicketJpaEntity> findByStatus(TicketStatus status, Pageable pageable);

    Page<TicketJpaEntity> findByUserId(UUID userId, Pageable pageable);

    Page<TicketJpaEntity> findByStatusAndUserId(TicketStatus status, UUID userId, Pageable pageable);

    List<TicketJpaEntity> findByUserId(UUID userId);
}
