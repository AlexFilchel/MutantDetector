package com.example.MutantDetector.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidDnaSequenceValidatorTest {

    private final ValidDnaSequenceValidator validator = new ValidDnaSequenceValidator();

    @Test
    void testAdnValidoNxN() {
        String[] adn = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        assertTrue(validator.isValid(adn, null));
    }

    @Test
    void testAdnNullNoEsValido() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void testAdnMenosDe4FilasNoEsValido() {
        String[] adn = {
                "ATG",
                "CAG",
                "TTA"
        };
        assertFalse(validator.isValid(adn, null));
    }

    @Test
    void testAdnNoCuadradoNoEsValido() {
        String[] adn = {
                "ATGC",
                "CAG",
                "TTAT"
        };
        assertFalse(validator.isValid(adn, null));
    }

    @Test
    void testAdnConCaracteresInvalidosNoEsValido() {
        String[] adn = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(validator.isValid(adn, null));
    }

    @Test
    void testFilaNullNoEsValida() {
        String[] adn = {
                "ATGC",
                null,
                "TTAT",
                "AGAC"
        };
        assertFalse(validator.isValid(adn, null));
    }
}
