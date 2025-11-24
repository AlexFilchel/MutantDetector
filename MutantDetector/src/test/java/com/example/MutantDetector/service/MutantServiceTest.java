package com.example.MutantDetector.service;

import com.example.MutantDetector.entity.DnaRecord;
import com.example.MutantDetector.exception.HashGenerationException;
import com.example.MutantDetector.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MutantServiceTest {

    private final DnaRecordRepository repository = mock(DnaRecordRepository.class);
    private final MutantDetector detector = mock(MutantDetector.class);

    private final MutantService service = new MutantService(repository, detector);

    @Test
    void testDevuelveResultadoCacheadoSiExisteEnBD() {
        String[] adn = {"ATGC", "CAGT", "TTAT", "AGAC"};

        DnaRecord record = DnaRecord.builder()
                .hashAdn("abc")
                .esMutante(true)
                .build();

        when(repository.findByHashAdn(any())).thenReturn(Optional.of(record));

        boolean resultado = service.esMutante(adn);

        assertTrue(resultado);
        verify(detector, never()).esMutante(any());
        verify(repository, never()).save(any());
    }

    @Test
    void testGuardaNuevoRegistroSiNoExisteYEsMutante() {
        String[] adn = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(repository.findByHashAdn(any())).thenReturn(Optional.empty());
        when(detector.esMutante(adn)).thenReturn(true);

        boolean resultado = service.esMutante(adn);

        assertTrue(resultado);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(repository).save(captor.capture());

        DnaRecord saved = captor.getValue();
        assertNotNull(saved.getHashAdn());
        assertTrue(saved.isEsMutante());
    }

    @Test
    void testGuardaNuevoRegistroSiNoExisteYEsHumano() {
        String[] adn = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(repository.findByHashAdn(any())).thenReturn(Optional.empty());
        when(detector.esMutante(adn)).thenReturn(false);

        boolean resultado = service.esMutante(adn);

        assertFalse(resultado);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(repository).save(captor.capture());

        DnaRecord saved = captor.getValue();
        assertNotNull(saved.getHashAdn());
        assertFalse(saved.isEsMutante());
    }

    @Test
    void testCalcularHashMismoAdnDaMismoResultado() {
        String[] adn = {"ATGC", "CAGT", "TTAT", "AGAC"};

        String hash1 = service.calcularHash(adn);
        String hash2 = service.calcularHash(adn);

        assertNotNull(hash1);
        assertEquals(hash1, hash2);
    }

    @Test
    void testCalcularHashAdnNullLanzaExcepcion() {
        assertThrows(HashGenerationException.class,
                () -> service.calcularHash(null));
    }

    @Test
    void testCalcularHashAdnVacioLanzaExcepcion() {
        assertThrows(HashGenerationException.class,
                () -> service.calcularHash(new String[]{}));
    }
}
