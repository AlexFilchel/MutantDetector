package com.example.MutantDetector.service;

import com.example.MutantDetector.dto.StatsResponse;
import com.example.MutantDetector.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository repository;

    public StatsResponse obtenerEstadisticas(){
        long mutantes = repository.countByEsMutante(true);
        long humanos = repository.countByEsMutante(false);

        double ratio = humanos == 0 ? 0.0 : (double) mutantes / humanos;

        return new StatsResponse(mutantes, humanos, ratio);
    }
}
