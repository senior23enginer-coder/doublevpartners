package com.doublev.ticketservice.infrastructure.web;

import com.doublev.ticketservice.application.ticket.TicketService;
import com.doublev.ticketservice.application.ticket.dto.*;
import com.doublev.ticketservice.domain.ticket.TicketStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(@Valid @RequestBody CreateTicketRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public TicketResponse update(@PathVariable UUID id,
                                 @Valid @RequestBody UpdateTicketRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public TicketResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<TicketResponse> list(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) UUID userId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return service.list(status, userId, pageable);
    }

    @GetMapping("/by-user/{userId}")
    public List<TicketResponse> listByUser(@PathVariable UUID userId) {
        return service.getTicketsByUser(userId);
    }
}
