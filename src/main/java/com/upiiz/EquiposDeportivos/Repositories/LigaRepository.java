package com.upiiz.EquiposDeportivos.Repositories;

import com.upiiz.EquiposDeportivos.Entities.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {
}
