package com.example.MutantDetector.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    // ========= CASOS MUTANTES =========

    @Test
    void testMutanteHorizontalYVertical() {
        String[] adn = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertTrue(detector.esMutante(adn));
    }

    @Test
    void testMutanteDiagonalDescendente() {
        String[] adn = {
                "ATGCGA",
                "CAGTAC",
                "TTAAGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };


        assertTrue(detector.esMutante(adn));
    }

    @Test
    void testMutanteVertical() {
        String[] adn = {
                "ATGC",
                "ATGC",
                "ATGC",
                "ATGC"
        };
        assertTrue(detector.esMutante(adn));
    }

    @Test
    void testMutanteDiagonalAscendente() {
        String[] adn = {
                "AAATG",
                "CAAAG",
                "TCAAG",
                "GTCAG",
                "GGTCA"
        };
        // Hay una diagonal ascendente de 'A'
        assertTrue(detector.esMutante(adn));
    }

    @Test
    void testMutanteMultiplesSecuenciasEarlyTermination() {
        String[] adn = {
                "AAAAAA",
                "CCCCCC",
                "GGGGGG",
                "TTTTTT",
                "AAAAAA",
                "CCCCCC"
        };
        // Muchísimas secuencias, debería ser mutante rápido
        assertTrue(detector.esMutante(adn));
    }

    @Test
    void testMutanteMatrizGrande10x10() {
        String[] adn = {
                "ATGCGAATGC",
                "CAGTGCTTGC",
                "TTATGTTTGT",
                "AGAAGGAGGG",
                "CCCCTAACCC",
                "TCACTGTTTT",
                "ATGCGAATGC",
                "CAGTGCTTGC",
                "TTATGTTTGT",
                "AGAAGGAGGG"
        };
        assertTrue(detector.esMutante(adn));
    }

    // ========= CASOS HUMANOS =========

    @Test
    void testHumanoSinSecuencias() {
        String[] adn = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.esMutante(adn));
    }

    @Test
    void testHumanoConUnaSecuencia() {
        String[] adn = {
                "AAAA",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        // Solo una secuencia → humano
        assertFalse(detector.esMutante(adn));
    }

    @Test
    void testHumanoMatriz5x5SinSecuencias() {
        String[] adn = {
                "ATGCG",
                "CAGTC",
                "TTATG",
                "AGACG",
                "GCGTA"
        };
        assertFalse(detector.esMutante(adn));
    }

    // ========= CASOS INVALIDOS / EDGE =========

    @Test
    void testAdnNoCuadrado() {
        String[] adn = {
                "ATGC",
                "CAG",
                "TTAT"
        };
        assertFalse(detector.esMutante(adn));
    }

    @Test
    void testCaracteresInvalidos() {
        String[] adn = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        // Con tu validación/DTO nunca llega esto, pero a nivel algoritmo lo tratamos como NO mutante
        assertFalse(detector.esMutante(adn));
    }

    @Test
    void testNull() {
        assertFalse(detector.esMutante(null));
    }

    @Test
    void testVacio() {
        String[] adn = {};
        assertFalse(detector.esMutante(adn));
    }

    @Test
    void testMatriz1x1() {
        String[] adn = {"A"};
        assertFalse(detector.esMutante(adn));
    }

    @Test
    void testMatriz2x2() {
        String[] adn = {
                "AA",
                "AA"
        };
        assertFalse(detector.esMutante(adn));
    }

    @Test
    void testMatriz3x3() {
        String[] adn = {
                "AAA",
                "AAA",
                "AAA"
        };
        assertFalse(detector.esMutante(adn));
    }
}
