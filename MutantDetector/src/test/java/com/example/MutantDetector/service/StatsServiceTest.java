package com.example.MutantDetector.service;

import com.example.MutantDetector.dto.StatsResponse;
import com.example.MutantDetector.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceTest {

    private final DnaRecordRepository repository = mock(DnaRecordRepository.class);
    private final StatsService statsService = new StatsService(repository);

    @Test
    void testEstadisticasCorrectas() {
        when(repository.countByEsMutante(true)).thenReturn(40L);
        when(repository.countByEsMutante(false)).thenReturn(100L);

        StatsResponse stats = statsService.obtenerEstadisticas();

        assertEquals(40, stats.count_mutant_dna());
        assertEquals(100, stats.count_human_dna());
        assertEquals(0.4, stats.ratio());
    }

    @Test
    void testRatioConHumanosCero() {
        when(repository.countByEsMutante(true)).thenReturn(10L);
        when(repository.countByEsMutante(false)).thenReturn(0L);

        StatsResponse stats = statsService.obtenerEstadisticas();

        assertEquals(0.0, stats.ratio());
    }
}
