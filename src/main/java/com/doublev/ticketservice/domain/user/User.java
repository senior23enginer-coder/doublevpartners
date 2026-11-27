package com.doublev.ticketservice.domain.user;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {

    private UUID id;
    private String nombres;
    private String apellidos;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public User(UUID id, String nombres, String apellidos,
                LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public static User newUser(String nombres, String apellidos) {
        LocalDateTime now = LocalDateTime.now();
        return new User(null, nombres, apellidos, now, now);
    }

    public void update(String nombres, String apellidos) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaActualizacion = LocalDateTime.now();
    }

}

