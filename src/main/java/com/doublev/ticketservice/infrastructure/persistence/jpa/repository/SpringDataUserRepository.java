package com.doublev.ticketservice.infrastructure.persistence.jpa.repository;

import com.doublev.ticketservice.infrastructure.persistence.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {
}
