package com.example.MutantDetector.repository;

import com.example.MutantDetector.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    Optional<DnaRecord> findByHashAdn(String hashAdn);

    long countByEsMutante(boolean esMutante);
}
