package com.upiiz.EquiposDeportivos.Repositories;

import com.upiiz.EquiposDeportivos.Entities.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}
