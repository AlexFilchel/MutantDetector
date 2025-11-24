package com.example.MutantDetector.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "adn_registros",
        indexes = {
                @Index(name = "idx_hash_adn", columnList = "hashAdn"),
                @Index(name = "idx_es_mutante", columnList = "esMutante")
        }
)
@Builder
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hashAdn", nullable = false, unique = true, length = 64)
    private String hashAdn;

    @Column(name = "esMutante")
    private boolean esMutante;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    void antesDeGuardar(){
        this.fechaCreacion = LocalDateTime.now();
    }
}
