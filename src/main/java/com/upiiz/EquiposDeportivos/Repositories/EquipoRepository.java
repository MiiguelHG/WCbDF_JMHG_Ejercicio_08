package com.upiiz.EquiposDeportivos.Repositories;

import com.upiiz.EquiposDeportivos.Entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
}
