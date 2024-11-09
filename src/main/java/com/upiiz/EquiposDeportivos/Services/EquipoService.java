package com.upiiz.EquiposDeportivos.Services;

import com.upiiz.EquiposDeportivos.Entities.Equipo;
import com.upiiz.EquiposDeportivos.Repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {
    @Autowired
    private EquipoRepository equipoRepository;

    // Guardar un equipo
    public Equipo save(Equipo equipo){
        return equipoRepository.save(equipo);
    }

    // Buscar todos los equipos
    public List<Equipo> findAll(){
        return equipoRepository.findAll();
    }

    // Buscar un equipo por id
    public Equipo findById(Long id){
        return equipoRepository.findById(id).orElse(null);
    }

    // Actualizar un equipo
    public Equipo update(Equipo equipo){
        if (equipoRepository.existsById(equipo.getId())){
            return equipoRepository.save(equipo);
        }
        return null;
    }

    // Eliminar un equipo
    public boolean deleteById(Long id){
        if (equipoRepository.existsById(id)){
            equipoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
