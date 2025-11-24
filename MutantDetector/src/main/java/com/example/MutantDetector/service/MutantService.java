package com.example.MutantDetector.service;

import com.example.MutantDetector.entity.DnaRecord;
import com.example.MutantDetector.exception.HashGenerationException;
import com.example.MutantDetector.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector detector;

    public boolean esMutante(String[] adn){
        String hash = calcularHash(adn);

        return repository.findByHashAdn(hash)
                .map(DnaRecord::isEsMutante)
                .orElseGet(()->{
                    boolean resultado = detector.esMutante(adn);

                    repository.save(
                            DnaRecord.builder()
                                    .hashAdn(hash)
                                    .esMutante(resultado)
                                    .build()
                    );
                    return resultado;
                });
    }

    public String calcularHash(String[] adn) {

        if (adn == null || adn.length == 0) {
            throw new HashGenerationException("ADN inválido para hashing", null);
        }

        String adnConcatenado = String.join("", adn);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(adnConcatenado.getBytes(StandardCharsets.UTF_8));

            // Convertir bytes a hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new HashGenerationException("Error al calcular hash SHA-256 del adn", e);
        }
    }

}
