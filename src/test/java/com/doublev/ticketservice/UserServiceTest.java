package com.doublev.ticketservice;

import com.doublev.ticketservice.application.user.UserService;
import com.doublev.ticketservice.application.user.dto.CreateUserRequest;
import com.doublev.ticketservice.application.user.dto.UpdateUserRequest;
import com.doublev.ticketservice.domain.user.User;
import com.doublev.ticketservice.domain.user.port.UserRepositoryPort;
import com.doublev.ticketservice.shared.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnUserResponse() {
        CreateUserRequest request = new CreateUserRequest("Juan", "Pérez");
        User user = User.newUser("Juan", "Pérez");
        User saved = new User(UUID.randomUUID(), "Juan", "Pérez",
                user.getFechaCreacion(), user.getFechaActualizacion());

        when(userRepository.save(any(User.class))).thenReturn(saved);

        var response = userService.create(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.nombres()).isEqualTo("Juan");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getById_nonExisting_throwsNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAll_shouldReturnList() {
        User u1 = User.newUser("A", "B");
        User u2 = User.newUser("C", "D");
        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        var list = userService.getAll();

        assertThat(list).hasSize(2);
    }

    @Test
    void update_shouldModifyUser() {
        UUID id = UUID.randomUUID();
        User existing = User.newUser("Old", "Name");
        existing.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = userService.update(id, new UpdateUserRequest("New", "Name"));

        assertThat(response.nombres()).isEqualTo("New");
        verify(userRepository).save(any(User.class));
    }
}
