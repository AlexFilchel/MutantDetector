package com.example.MutantDetector.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Estructura de error para las respuestas del servidor")
public record ErrorResponse(
        @Schema(description = "Momento en que ocurrió el error", example = "2025-11-11T15:30:00")
        LocalDateTime timestamp,

        @Schema(description = "Código de estado HTTP", example = "400")
        int status,

        @Schema(description = "Tipo de error", example = "Bad Request")
        String error,

        @Schema(description = "Mensaje descriptivo del error", example = "La secuencia de ADN no puede estar vacía")
        String message,

        @Schema(description = "Ruta del endpoint que causó el error", example = "/mutant")
        String path
) {}
