package com.example.MutantDetector.dto;

import com.example.MutantDetector.validation.ValidDnaSequence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request para verificar si un ADN pertenece a un mutante")
public record DnaRequest(
        @Schema(description = "Matriz NxN de secuencias de ADN", example = "[\"ATGCGA\", \"CAGTGC\", \"TTATGT\", \"AGAAGG\", \"CCCCTA\", \"TCACTG\"]")
        @NotEmpty(message = "La secuencia de ADN no puede estar vacía")
        @ValidDnaSequence
        String[] dna
) {}
