package com.example.MutantDetector.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estadísticas de las verificaciones de ADN")
public record StatsResponse(
        @Schema(description = "Cantidad total de ADN mutante analizado", example = "40")
        long count_mutant_dna,

        @Schema(description = "Cantidad total de ADN humano analizado", example = "100")
        long count_human_dna,

        @Schema(description = "Relación entre mutantes y humanos", example = "0.4")
        double ratio
) {}
