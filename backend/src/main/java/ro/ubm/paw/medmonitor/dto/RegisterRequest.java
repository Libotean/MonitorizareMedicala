package ro.ubm.paw.medmonitor.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank @Email @Size(max = 150) String email,
        // BCrypt foloseste doar primii 72 de bytes
        @NotBlank @Size(min = 6, max = 72) String parola,
        @NotBlank @Size(max = 100) String nume,
        @NotBlank @Size(max = 100) String prenume,
        @NotNull @Past LocalDate dataNasterii
) { }
