package com.doublev.ticketservice.infrastructure.web;

import com.doublev.ticketservice.application.user.UserService;
import com.doublev.ticketservice.application.user.dto.*;
import com.doublev.ticketservice.shared.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones CRUD de usuarios")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Crear un usuario",
            description = "Crea un nuevo usuario con nombres y apellidos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario creado",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza nombres y apellidos de un usuario existente."
    )
    public UserResponse update(@PathVariable UUID id,
                               @Valid @RequestBody UpdateUserRequest request) {
        return service.update(id, request);
    }

    @GetMapping
    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene todos los usuarios registrados."
    )
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Obtiene la información de un usuario específico."
    )
    public UserResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }
}
