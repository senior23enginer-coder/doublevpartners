# Ticket Service – Prueba Técnica Double V Partners

Spring Initializr pluging 
Backend desarrollado en **Java 17 / Spring Boot 3.5.7**, siguiendo **arquitectura hexagonal**, que expone un sistema de gestión de **Usuarios** y **Tickets** con:

- CRUD de Usuarios
- CRUD de Tickets
- Filtros por `status` y `userId` + paginación
- Autenticación y autorización con **JWT**
- Caché de tickets por usuario
- Persistencia en **H2 en memoria** con Spring Data JPA
- Documentación **OpenAPI / Swagger**
- Tests **unitarios** y **de integración**
- Imagen **Docker** lista para levantar el servicio

---

## 1. Tecnologías

- Java 17
- Spring Boot 3.5.7
- Spring Web, Spring Data JPA, Spring Security
- H2 Database
- JWT (JSON Web Tokens)
- Springdoc OpenAPI (Swagger UI)
- JUnit 5, Mockito
- Docker

---

## 2. Cómo levantar el proyecto en local

### 2.1 Requisitos

- Java 17
- Maven 3.8+
- Docker

### 2.2 Ejecución con Maven

```bash
mvn clean spring-boot:run

### Docker 
# build
docker build -t ticket-service .

# run
docker run -p 8080:8080 ticket-service

documentacion
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/h2-console
http://localhost:8080/v3/api-docs