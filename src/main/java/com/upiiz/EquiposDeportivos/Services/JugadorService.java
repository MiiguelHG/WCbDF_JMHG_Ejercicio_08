package com.upiiz.EquiposDeportivos.Services;

import com.upiiz.EquiposDeportivos.Entities.Jugador;
import com.upiiz.EquiposDeportivos.Repositories.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;

    // Guardar un jugador
    public Jugador save(Jugador jugador){
        return jugadorRepository.save(jugador);
    }

    // Buscar todos los jugadores
    public List<Jugador> findAll(){
        return jugadorRepository.findAll();
    }

    // Buscar un jugador por id
    public Jugador findById(Long id){
        return jugadorRepository.findById(id).orElse(null);
    }

    // Actualizar un jugador
    public Jugador update(Jugador jugador){
        if (jugadorRepository.existsById(jugador.getId())){
            return jugadorRepository.save(jugador);
        }
        return null;
    }

    // Eliminar un jugador
    public boolean deleteById(Long id){
        if (jugadorRepository.existsById(id)){
            jugadorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
