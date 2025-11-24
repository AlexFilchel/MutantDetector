package com.example.MutantDetector.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    @Override
    public boolean isValid(String[] adn, ConstraintValidatorContext context){
        if (adn == null || adn.length < 4) return false;

        int n = adn.length;

        for(String fila : adn){
            if(fila == null)return false;

            if(fila.length() != n) return false;

            if (!fila.matches("^[ATCG]+$")) return false;
        }

        return true;
    }
}
