package com.upiiz.EquiposDeportivos.Services;

import com.upiiz.EquiposDeportivos.Entities.Liga;
import com.upiiz.EquiposDeportivos.Repositories.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigaService {
    @Autowired
    private LigaRepository ligaRepository;

    // Guardar una liga
    public Liga save(Liga liga){
        return ligaRepository.save(liga);
    }

    // Buscar todas las ligas
    public List<Liga> findAll(){
        return ligaRepository.findAll();
    }

    // Buscar una liga por id
    public Liga findById(Long id){
        return ligaRepository.findById(id).orElse(null);
    }

    // Actualizar una liga
    public Liga update(Liga liga){
        if (ligaRepository.existsById(liga.getId())){
            return ligaRepository.save(liga);
        }
        return null;
    }

    // Eliminar una liga
    public boolean deleteById(Long id){
        if (ligaRepository.existsById(id)){
            ligaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
