package com.example.MutantDetector.controller;


import com.example.MutantDetector.dto.DnaRequest;
import com.example.MutantDetector.dto.StatsResponse;
import com.example.MutantDetector.service.MutantService;
import com.example.MutantDetector.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "Mutant Detector", description = "API para detección de mutantes y estadísticas de ADN" )
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Verifica si un ADN pertenece a un mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El ADN pertenece a un mutante"),
            @ApiResponse(responseCode = "403", description = "El ADN pertenece a un humano"),
            @ApiResponse(responseCode = "400", description = "Secuencia de ADN inválida")
    })
    public ResponseEntity<Void> verificarMutante(@Valid @RequestBody DnaRequest request){
        boolean esMutante = mutantService.esMutante(request.dna());

        return esMutante
                ? ResponseEntity.ok().build() // 200 OK
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtiene estadísticas globales de análisis de ADN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas recuperadas correctamente")
    })
    public ResponseEntity<StatsResponse> obtenerEstadisticas(){
        StatsResponse stats = statsService.obtenerEstadisticas();
        return ResponseEntity.ok(stats);
    }
}
