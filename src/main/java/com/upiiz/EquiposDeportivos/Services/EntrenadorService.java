package com.upiiz.EquiposDeportivos.Services;

import com.upiiz.EquiposDeportivos.Entities.Entrenador;
import com.upiiz.EquiposDeportivos.Repositories.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorService {
    @Autowired
    private EntrenadorRepository entrenadorRepository;

    // Guardar un entrenador
    public Entrenador save(Entrenador entrenador){
        return entrenadorRepository.save(entrenador);
    }

    // Buscar todos los entrenadores
    public List<Entrenador> findAll(){
        return entrenadorRepository.findAll();
    }

    // Buscar un entrenador por su id
    public Entrenador findById(Long id){
        return entrenadorRepository.findById(id).orElse(null);
    }

    // Actualizar un entrenador
    public Entrenador update(Entrenador entrenador){
        if (entrenadorRepository.existsById(entrenador.getId())){
            return entrenadorRepository.save(entrenador);
        }
        return null;
    }

    // Eliminar un entrenador
    public boolean deleteById(Long id){
        if (entrenadorRepository.existsById(id)){
            entrenadorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
