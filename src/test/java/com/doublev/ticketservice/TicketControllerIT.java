package com.doublev.ticketservice;

import com.doublev.ticketservice.application.ticket.dto.CreateTicketRequest;
import com.doublev.ticketservice.domain.ticket.TicketStatus;
import com.doublev.ticketservice.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.doublev.ticketservice.infrastructure.persistence.jpa.repository.SpringDataUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringDataUserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        UserJpaEntity user = new UserJpaEntity();
        user.setNombres("Test");
        user.setApellidos("User");
        UserJpaEntity saved = userRepository.save(user);
        userId = saved.getId();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createTicket_shouldReturn201() throws Exception {
        CreateTicketRequest request =
                new CreateTicketRequest("Ticket de prueba", userId, TicketStatus.ABIERTO);

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descripcion").value("Ticket de prueba"))
                .andExpect(jsonPath("$.userId").value(userId.toString()));
    }
}

