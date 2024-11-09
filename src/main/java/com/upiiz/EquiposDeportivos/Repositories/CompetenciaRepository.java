package com.upiiz.EquiposDeportivos.Repositories;

import com.upiiz.EquiposDeportivos.Entities.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
}
