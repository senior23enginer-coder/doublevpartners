package com.doublev.ticketservice;

import com.doublev.ticketservice.application.ticket.TicketService;
import com.doublev.ticketservice.application.ticket.dto.CreateTicketRequest;
import com.doublev.ticketservice.application.ticket.dto.UpdateTicketRequest;
import com.doublev.ticketservice.domain.ticket.*;
import com.doublev.ticketservice.domain.ticket.port.TicketRepositoryPort;
import com.doublev.ticketservice.shared.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepositoryPort ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Ticket sampleTicket(UUID userId) {
        return new Ticket(
                UUID.randomUUID(),
                "desc",
                userId,
                TicketStatus.ABIERTO,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void create_shouldSaveTicket() {
        UUID userId = UUID.randomUUID();
        CreateTicketRequest request =
                new CreateTicketRequest("desc", userId, TicketStatus.ABIERTO);

        Ticket created = Ticket.newTicket("desc", userId, TicketStatus.ABIERTO);
        Ticket saved = sampleTicket(userId);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(saved);

        var response = ticketService.create(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.userId()).isEqualTo(userId);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void getById_notFound_throws() {
        UUID id = UUID.randomUUID();
        when(ticketRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.getById(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_shouldChangeStatusAndDescription() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Ticket existing = sampleTicket(userId);
        existing.setId(id);

        when(ticketRepository.findById(id)).thenReturn(Optional.of(existing));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateTicketRequest request =
                new UpdateTicketRequest("nueva desc", TicketStatus.CERRADO);

        var response = ticketService.update(id, request);

        assertThat(response.status()).isEqualTo(TicketStatus.CERRADO);
        assertThat(response.descripcion()).isEqualTo("nueva desc");
    }

    @Test
    void list_shouldReturnPage() {
        UUID userId = UUID.randomUUID();
        Ticket t = sampleTicket(userId);
        Page<Ticket> page = new PageImpl<>(List.of(t));

        when(ticketRepository.findAll(any(Pageable.class))).thenReturn(page);

        var result = ticketService.list(null, null, PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
    }
}
