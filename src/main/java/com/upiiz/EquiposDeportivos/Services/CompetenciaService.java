package com.upiiz.EquiposDeportivos.Services;

import com.upiiz.EquiposDeportivos.Entities.Competencia;
import com.upiiz.EquiposDeportivos.Repositories.CompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenciaService {
    @Autowired
    private CompetenciaRepository competenciaRepository;

    // Guardar una competencia
    public Competencia save(Competencia competencia){
        return competenciaRepository.save(competencia);
    }

    // Buscar todas las competencias
    public List<Competencia> findAll(){
        return competenciaRepository.findAll();
    }

    // Buscar una competencia por id
    public Competencia findById(Long id){
        return competenciaRepository.findById(id).orElse(null);
    }

    // Actualizar una competencia
    public Competencia update(Competencia competencia) {
        if (competenciaRepository.existsById(competencia.getId())) {
            return competenciaRepository.save(competencia);
        }
        return null;
    }

    // Eliminar una competencia
    public boolean deleteById(Long id){
        if (competenciaRepository.existsById(id)){
            competenciaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
